package gitApi.connector.Classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Owner {
	@JsonProperty("login")
	public String Ownerlogin;
	@JsonProperty("id")
	public int Ownerid;
	@JsonProperty("node_id")
	public String Ownernode_id;
	public String Owneravatar_url;
	public String Ownergravatar_id;
	public String Ownerurl;
	public String Ownerhtml_url;
	public String Ownerfollowers_url;
	public String Ownerfollowing_url;
	public String Ownergists_url;
	public String Ownerstarred_url;
	public String Ownersubscriptions_url;
	public String Ownerorganizations_url;
	@JsonProperty("repos_url")
	public String Ownerrepos_url;
	public String Ownerevents_url;
	public String Ownerreceived_events_url;
	public String Ownertype;
	public boolean Ownersite_admin;

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

	public String getOwnernode_id() {
		return Ownernode_id;
	}

	public void setOwnernode_id(String ownernode_id) {
		Ownernode_id = ownernode_id;
	}

	public String getOwneravatar_url() {
		return Owneravatar_url;
	}

	public void setOwneravatar_url(String owneravatar_url) {
		Owneravatar_url = owneravatar_url;
	}

	public String getOwnergravatar_id() {
		return Ownergravatar_id;
	}

	public void setOwnergravatar_id(String ownergravatar_id) {
		Ownergravatar_id = ownergravatar_id;
	}

	public String getOwnerurl() {
		return Ownerurl;
	}

	public void setOwnerurl(String ownerurl) {
		Ownerurl = ownerurl;
	}

	public String getOwnerhtml_url() {
		return Ownerhtml_url;
	}

	public void setOwnerhtml_url(String ownerhtml_url) {
		Ownerhtml_url = ownerhtml_url;
	}

	public String getOwnerfollowers_url() {
		return Ownerfollowers_url;
	}

	public void setOwnerfollowers_url(String ownerfollowers_url) {
		Ownerfollowers_url = ownerfollowers_url;
	}

	public String getOwnerfollowing_url() {
		return Ownerfollowing_url;
	}

	public void setOwnerfollowing_url(String ownerfollowing_url) {
		Ownerfollowing_url = ownerfollowing_url;
	}

	public String getOwnergists_url() {
		return Ownergists_url;
	}

	public void setOwnergists_url(String ownergists_url) {
		Ownergists_url = ownergists_url;
	}

	public String getOwnerstarred_url() {
		return Ownerstarred_url;
	}

	public void setOwnerstarred_url(String ownerstarred_url) {
		Ownerstarred_url = ownerstarred_url;
	}

	public String getOwnersubscriptions_url() {
		return Ownersubscriptions_url;
	}

	public void setOwnersubscriptions_url(String ownersubscriptions_url) {
		Ownersubscriptions_url = ownersubscriptions_url;
	}

	public String getOwnerorganizations_url() {
		return Ownerorganizations_url;
	}

	public void setOwnerorganizations_url(String ownerorganizations_url) {
		Ownerorganizations_url = ownerorganizations_url;
	}

	public String getOwnerrepos_url() {
		return Ownerrepos_url;
	}

	public void setOwnerrepos_url(String ownerrepos_url) {
		Ownerrepos_url = ownerrepos_url;
	}

	public String getOwnerevents_url() {
		return Ownerevents_url;
	}

	public void setOwnerevents_url(String ownerevents_url) {
		Ownerevents_url = ownerevents_url;
	}

	public String getOwnerreceived_events_url() {
		return Ownerreceived_events_url;
	}

	public void setOwnerreceived_events_url(String ownerreceived_events_url) {
		Ownerreceived_events_url = ownerreceived_events_url;
	}

	public String getOwnertype() {
		return Ownertype;
	}

	public void setOwnertype(String ownertype) {
		Ownertype = ownertype;
	}

	public boolean isOwnersite_admin() {
		return Ownersite_admin;
	}

	public void setOwnersite_admin(boolean ownersite_admin) {
		Ownersite_admin = ownersite_admin;
	}

}
