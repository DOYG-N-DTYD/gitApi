package gitApi.connector.Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import service.RepositoryService;

@Getter
@Setter
public class GitUserRepositories implements Serializable {
	private int id;
	private String node_id;
	private String name;
	private String full_name;
	private String branches_url;
	private boolean fork;
	private Owner owner;
	private GitUserRepositories gitUserRepositories;
	
//	public boolean isFork() {
//		return fork;
//	}
//
//	public void setFork(boolean fork) {
//		this.fork = fork;
//	}
//
//	public String getBranches_url() {
//		return branches_url;
//	}
//
//	public void setBranches_url(String branches_url) {
//		this.branches_url = branches_url;
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public Owner getOwner() {
//		return owner;
//	}
//
//	public void setOwner(Owner owner) {
//		this.owner = owner;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getNode_id() {
//		return node_id;
//	}
//
//	public void setNode_id(String node_id) {
//		this.node_id = node_id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getFull_name() {
//		return full_name;
//	}
//
//	public void setFull_name(String full_name) {
//		this.full_name = full_name;
//	}
}
