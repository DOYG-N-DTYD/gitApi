package gitApi.connector.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Commit {
	@JsonProperty("sha")
	public String sha;
	@JsonProperty("url")
	public String url;
    
	public String getSha() {
		return sha;
	}
	public void setSha(String sha) {
		this.sha = sha;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
