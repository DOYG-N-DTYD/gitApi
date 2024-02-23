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
}
