package com.yogesh.Portfolio_Backend.repository.project_repository;

import com.yogesh.Portfolio_Backend.model.documents.project.Project;
import com.yogesh.Portfolio_Backend.repository.DynamoDBRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectRepo {
    private final DynamoDBRepository<Project> dynamoDBRepository;

    public ProjectRepo(DynamoDBRepository<Project> dynamoDBRepository) {
        this.dynamoDBRepository = dynamoDBRepository;
    }

    public Project save(Project project) throws Exception {
        return dynamoDBRepository.save(project);
    }

    public Project findById(String id) throws Exception {
        return dynamoDBRepository.findById(id, Project.class);
    }

    public List<Project> findAll() throws Exception {
        return dynamoDBRepository.findAll(Project.class);
    }

    public void delete(Project project) throws Exception {
        dynamoDBRepository.delete(project);
    }
}