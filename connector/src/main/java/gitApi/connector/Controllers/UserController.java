package gitApi.connector.Controllers;

import java.util.HashMap;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	private ResponseEntity<GitUser> gitUser;
	private ResponseEntity<GitUserRepositories[]> gitUserRepositories;
	private ResponseEntity<GitUserRepositoryBranches[]> gitUserRepositoryBranches;
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
		this.viewDataList = new LinkedList(); 
		try {
			getData(username, model);
		} catch (HttpClientErrorException e) {
			JSONObject errorResponseJsonObject = new JSONObject();
			try {
				// curl ok, model->html->parse
				errorResponseJsonObject.put("errorStatus", e.getStatusCode() + "<br>");
				errorResponseJsonObject.put("errorMessage", "username : " + username + " " + e.getMessage());
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return new ResponseEntity<>(errorResponseJsonObject.toString(), HttpStatus.CREATED);
		}
		getUserRepos();
		return new ResponseEntity<>(viewDataList, HttpStatus.CREATED);
	}

	// public String RepoViewDataConverter() {
	// 	StringBuilder outData = new StringBuilder();
	// 	for (RepoViewData data : viewDataList) {
	// 		outData.append(data.convertToHtml());
	// 	}
	// 	return outData.toString();
	// }

	public String getData(String username, Model model) throws HttpClientErrorException {
		log.info("getData");
		// https://api.github.com/users/DOYG-N-DTYD
		this.gitUser = restTemplate.exchange(uriString + username, HttpMethod.GET, httpRequest(), GitUser.class);
		return "gituserinformation";
	}

	public void getUserRepos() {
		log.info("getUserRepos");
		this.gitUserRepositories = restTemplate.exchange(gitUser.getBody().getRepos_url(), HttpMethod.GET, httpRequest(),
				GitUserRepositories[].class);
		System.out.println(this.gitUserRepositories.getBody());
		for (GitUserRepositories repo : gitUserRepositories.getBody()) {
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

		this.gitUserRepositoryBranches = restTemplate.exchange(urlStringBuilder.toString(), HttpMethod.GET, httpRequest(),
				GitUserRepositoryBranches[].class);

		for (GitUserRepositoryBranches gitUserRepositoryBranches : gitUserRepositoryBranches.getBody()) {

			String branch = gitUserRepositoryBranches.getBranchName();
			String sha = gitUserRepositoryBranches.getBranchCommits().sha;
			dataForView.setBranchSha(branch, sha);
		}
	}

	@PostMapping("/gituserinformation")
	public String usernameField(@RequestParam("userName") String userName, Model model) {
		log.info("usernameField");
		this.viewDataList = new LinkedList(); // when press button send clear previous results
		try {
			getData(userName, model);
		} catch (HttpClientErrorException e) {
			model.addAttribute("errorStatus", e.getStatusCode());
			model.addAttribute("errorMessage", userName + " " + e.getMessage());
			return "error";
		} catch (Exception e) {
			model.addAttribute("errorStatus", e.getMessage());
			model.addAttribute("errorMessage", e.toString());
			return "error";
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

	public HttpEntity httpRequest(){
		String tokenString = "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";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setBearerAuth(tokenString);
		return new HttpEntity<String>(httpHeaders); // request
	}
}
