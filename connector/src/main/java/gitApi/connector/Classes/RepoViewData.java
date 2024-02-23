package gitApi.connector.Classes;

import java.util.HashMap;
import java.util.LinkedList;

public class RepoViewData {
	private String repositoryName;
	private String ownersLogin;
	private HashMap<String, String> branchSha = new HashMap<>();

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getOwnersLogin() {
		return ownersLogin;
	}

	public void setOwnersLogin(String ownersLogin) {
		this.ownersLogin = ownersLogin;
	}

	public HashMap<String, String> getBranchSha() {
		return branchSha;
	}

	public void setBranchSha(String branch, String sha) {
		this.branchSha.put(branch, sha);
	}

	public String convertToHtml() {
		StringBuilder htmlString = new StringBuilder();
		htmlString.append("{ <br>");
		htmlString.append("&#20	\"repositoryName\" : " + repositoryName + "<br>");
		htmlString.append("&#20	\"ownersLogin\" : " + ownersLogin + "<br>");
		branchSha.forEach((branchName, SHA) -> {
			htmlString.append("&#20	&#20");
			htmlString.append(branchName);
			htmlString.append(" : ");
			htmlString.append(SHA);
		});
		htmlString.append("<br>");
		htmlString.append("}<br>");
		return htmlString.toString();
	}

}
