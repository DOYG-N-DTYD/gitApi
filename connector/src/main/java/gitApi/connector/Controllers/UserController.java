package gitApi.connector.Controllers;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import gitApi.connector.Classes.GitUser;
import gitApi.connector.Classes.GitUserRepositories;
import gitApi.connector.Classes.GitUserRepositoryBranches;
import service.RepositoryService;

@Controller
//@RequestMapping("/gituserinformation")
public class UserController {
	private final RestTemplate restTemplate;
	private final Logger log;
	private final String uriString;

	private GitUser gitUser;
	private GitUserRepositories[] gitUserRepositories;
	private GitUserRepositoryBranches[] gitUserRepositoryBranches;

	private RepositoryService repositoryService;
	private JSONObject jsonResponse = new JSONObject();
	//public List<String> repoNames = new LinkedList<>();
	
	public UserController() {
		this.uriString = "https://api.github.com/users/";
		this.restTemplate = new RestTemplate();
		this.log = LoggerFactory.getLogger(UserController.class);
	}

	@GetMapping(path = "/gituserinformation")
	@ResponseStatus(HttpStatus.OK)
	public String showGitUserInformation(Model model) {
		
		model.addAttribute("repositories", repositoryService.listAllRepositories());
		
		return "gituserinformation";
	}

	@RequestMapping(method = RequestMethod.GET, path = "gituserinformation/{username}")
	public @ResponseBody ResponseEntity getEntity(@PathVariable("username") String username) {
		log.info("### getEntity ###");

		getData(username);
		getUserRepos();
		try {
			jsonResponse.put("1","2");
			jsonResponse.put("3","4");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.CREATED);
		//return new ResponseEntity<>(repoNames.toString(), HttpStatus.CREATED);
	}

	public GitUser getData(String username) {
		try {
			String result = restTemplate.getForObject(uriString + username, String.class);
		} catch (HttpClientErrorException e) {
			// TODO:
			String errorMessageString = "{\n" + "\n" + "    “status”: ${responseCode}\n" // error 500
					+ "\n" + "    “message”: ${whyHasItHappened}\n" // user do not exists
					+ "\n" + "}";
			System.out.println(errorMessageString);

			// return errorMessageString;
		}

		this.gitUser = restTemplate.getForObject(uriString + username, GitUser.class);
		log.info("### getData ### " + gitUser.repos_url);
		return gitUser;
	}

	public void getUserRepos() {
		this.gitUserRepositories = restTemplate.getForObject(gitUser.repos_url, GitUserRepositories[].class);
		log.info("### getData ### " + gitUserRepositories);
		for (GitUserRepositories repo : gitUserRepositories) {
			if (repo.isFork()) {
				continue;
			}
			System.out.println("repo.getName()) 			:" + repo.getName());
			System.out.println("repo.getOwner().Ownerlogin 	:" + repo.getOwner().Ownerlogin);
			getUserReposBranches(repo);
				//System.out.println("!!!!!!!!!!!!!!!!!!!!!! " + jsonResponse.toString());
				try {
					jsonResponse.put("Repository name :",repo.getName());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//repoNames.add(repo.getName());

		}
		
	}

	public void getUserReposBranches(GitUserRepositories repository) {
		// https://api.github.com/repos/DOYG-N-DTYD/AndroidBookApp/branches{/branch}
		String repositoryBranchesUrlString = repository.getBranches_url();
		// https://api.github.com/repos/DOYG-N-DTYD/AndroidBookApp/branches
		StringBuilder urlStringBuilder = new StringBuilder(
				repositoryBranchesUrlString.substring(0, repositoryBranchesUrlString.indexOf("{")));

		this.gitUserRepositoryBranches = restTemplate.getForObject(urlStringBuilder.toString(),
				GitUserRepositoryBranches[].class);

		for (GitUserRepositoryBranches gitUserRepositoryBranches : gitUserRepositoryBranches) {
			System.out
					.println("gitUserRepositoryBranches.getBranchName() " + gitUserRepositoryBranches.getBranchName());
			System.out.println("gitUserRepositoryBranches.getBranchCommits().sha "
					+ gitUserRepositoryBranches.getBranchCommits().sha);
		}
	}

	//TODO: not working
	@PostMapping("/gituserinformation")
	public String usernameField(@RequestParam("userName") String userName) {
		System.out.println("gituserinformation/" +userName);
		return "redirect:/gituserinformation/" + userName;
	}
}
