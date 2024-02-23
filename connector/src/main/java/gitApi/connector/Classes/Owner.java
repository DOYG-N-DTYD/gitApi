package gitApi.connector.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Owner {
	@JsonProperty("login")
	public String Ownerlogin;
	@JsonProperty("id")
	public int Ownerid;
	
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
