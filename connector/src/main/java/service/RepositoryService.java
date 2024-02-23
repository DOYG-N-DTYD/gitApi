package service;

import java.util.List;

import org.springframework.stereotype.Service;

import gitApi.connector.Classes.GitUserRepositories;

@Service
public interface RepositoryService {
	List<GitUserRepositories> listAllRepositories();
}
