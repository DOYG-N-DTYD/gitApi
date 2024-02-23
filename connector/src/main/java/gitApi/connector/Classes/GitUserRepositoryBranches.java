package gitApi.connector.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitUserRepositoryBranches {
	@JsonProperty("name") 
	private String branchName;
	@JsonProperty("commit") 
	private Commit branchCommits;
    @JsonProperty("protected") 
    private boolean branchProtected;
    
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public Commit getBranchCommits() {
		return branchCommits;
	}
	public void setBranchCommits(Commit branchCommits) {
		this.branchCommits = branchCommits;
	}
	public boolean isBranchProtected() {
		return branchProtected;
	}
	public void setBranchProtected(boolean branchProtected) {
		this.branchProtected = branchProtected;
	}
}
