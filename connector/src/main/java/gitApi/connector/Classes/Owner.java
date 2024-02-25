package gitApi.connector.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Owner {
	@JsonProperty("login")
	private String Ownerlogin;
	@JsonProperty("id")
	private int Ownerid;
	
	public String getOwnerlogin() {
		return Ownerlogin;
	}
	public void setOwnerlogin(String ownerlogin) {
		Ownerlogin = ownerlogin;
	}
	public int getOwnerid() {
		return Ownerid;
	}
	public void setOwnerid(int ownerid) {
		Ownerid = ownerid;
	}
	
}
