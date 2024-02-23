package gitApi.connector.Controllers;

import java.util.HashMap;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import gitApi.connector.Classes.GitUser;
import gitApi.connector.Classes.GitUserRepositories;
import gitApi.connector.Classes.GitUserRepositoryBranches;
import gitApi.connector.Classes.RepoViewData;

@Controller
//@RequestMapping("/gituserinformation")
public class UserController {
	private final RestTemplate restTemplate;
	private final Logger log;
	private final String uriString;
	private GitUser gitUser;
	private GitUserRepositories[] gitUserRepositories;
	private GitUserRepositoryBranches[] gitUserRepositoryBranches;
	// private RepositoryService repositoryService;
	private JSONObject jsonResponse = new JSONObject();
	private LinkedList<RepoViewData> viewDataList = new LinkedList();

	public UserController() {
		this.uriString = "https://api.github.com/users/";
		this.restTemplate = new RestTemplate();
		this.log = LoggerFactory.getLogger(UserController.class);
	}

	@GetMapping(path = "/gituserinformation")
	public String showGitUserInformation(Model model) {
		// model.addAttribute("repositories", repositoryService.listAllRepositories());
		return "gituserinformation";
	}

	@RequestMapping(method = RequestMethod.GET, path = "gituserinformation/{username}")
	public @ResponseBody ResponseEntity getEntity(@PathVariable("username") String username, Model model) {
		log.info("getEntity");
		try {
			getData(username, model);
		} catch (HttpClientErrorException e) {
			JSONObject errorResponseJsonObject = new JSONObject();
			try {
				//TODO: json look
				errorResponseJsonObject.put("errorStatus", e.getStatusCode());
				errorResponseJsonObject.put("errorMessage", e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return new ResponseEntity<>(errorResponseJsonObject.toString(), HttpStatus.CREATED);
		}
		getUserRepos();
		return new ResponseEntity<>(RepoViewDataConverter(), HttpStatus.CREATED);
	}

	public String RepoViewDataConverter() {
		StringBuilder outData = new StringBuilder();
		for (RepoViewData data : viewDataList) {
			outData.append(data.convertToHtml());
		}
		return outData.toString();
	}

	public String getData(String username, Model model) throws HttpClientErrorException {
		log.info("getData");
		this.gitUser = restTemplate.getForObject(uriString + username, GitUser.class);
		return "gituserinformation";
	}

	public void getUserRepos() {
		log.info("getUserRepos");
		this.gitUserRepositories = restTemplate.getForObject(gitUser.repos_url, GitUserRepositories[].class);
		for (GitUserRepositories repo : gitUserRepositories) {
			if (repo.isFork()) {
				continue;
			}
			RepoViewData dataForView = new RepoViewData();
			dataForView.setRepositoryName(repo.getName());
			dataForView.setOwnersLogin(repo.getOwner().Ownerlogin);
			getUserReposBranches(repo, dataForView);
			try {
				jsonResponse.put(jsonResponse.length() + " ", repo.getName());
			} catch (JSONException e) {
				log.info(e.toString());
			}
			viewDataList.add(dataForView);
		}
	}

	public void getUserReposBranches(GitUserRepositories repository, RepoViewData dataForView) {
		log.info("getUserReposBranches");
		// https://api.github.com/repos/DOYG-N-DTYD/AndroidBookApp/branches{/branch}
		String repositoryBranchesUrlString = repository.getBranches_url();
		// https://api.github.com/repos/DOYG-N-DTYD/AndroidBookApp/branches
		StringBuilder urlStringBuilder = new StringBuilder(
				repositoryBranchesUrlString.substring(0, repositoryBranchesUrlString.indexOf("{")));

		this.gitUserRepositoryBranches = restTemplate.getForObject(urlStringBuilder.toString(),
				GitUserRepositoryBranches[].class);
		
		for (GitUserRepositoryBranches gitUserRepositoryBranches : gitUserRepositoryBranches) {

			String branch = gitUserRepositoryBranches.getBranchName();
			String sha = gitUserRepositoryBranches.getBranchCommits().sha;
			dataForView.setBranchSha(branch, sha);
		}
	}

	@PostMapping("/gituserinformation")
	public String usernameField(@RequestParam("userName") String userName, Model model) {
		log.info("usernameField");
		try {
			getData(userName, model);
		} catch (HttpClientErrorException e) {
			model.addAttribute("errorStatus", e.getStatusCode());
			model.addAttribute("errorMessage", userName + " " + e.getMessage());
			return "error";
		} catch (Exception e) {
			model.addAttribute("errorStatus", e.getMessage());
			model.addAttribute("errorMessage", e.toString());
		}
		getUserRepos();
		processDataToView(model);
		return "gituserinformation";
	}

	public void processDataToView(Model model) {
		log.info("usernameField");
		model.addAttribute("viewDataList", this.viewDataList);
		for (RepoViewData repoViewData : this.viewDataList) {
			HashMap<String, String> branchSha = repoViewData.getBranchSha();
			// branchSha.forEach((key, val) -> System.out.println("Branch name : " + key + "
			// " + "SHA : " + val));
		}
	}
}
