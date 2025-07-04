package com.yogesh.Portfolio_Backend.service.project_service;

import com.yogesh.Portfolio_Backend.model.documents.project.Project;
import com.yogesh.Portfolio_Backend.repository.project_repository.ProjectRepo;
import com.yogesh.Portfolio_Backend.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProjectService extends BaseService {
    private final ProjectRepo projectRepo;

    public ProjectService(ProjectRepo projectRepo, S3Client s3Client) {
        super(s3Client);
        this.projectRepo = projectRepo;
    }

    public Project createProject(Project project, MultipartFile image) throws Exception {
        if (image != null && !image.isEmpty()) {
            String imageUrl = uploadFile(image.getOriginalFilename(), image.getBytes());
            project.setImageUrl(imageUrl);
        }
        project.setId(UUID.randomUUID().toString());
        return projectRepo.save(project);
    }

    public Project getProject(String id) throws Exception {
        Project project = projectRepo.findById(id);
        if (project == null) throw new NoSuchElementException("Project not found: " + id);
        return project;
    }

    public List<Project> getAllProjects() throws Exception {
        return projectRepo.findAll();
    }

    public Project updateProject(String id, Project project, MultipartFile image) throws Exception {
        Project existing = getProject(id);
        if (image != null && !image.isEmpty()) {
            if (existing.getImageUrl() != null) deleteFile(existing.getImageUrl());
            String imageUrl = uploadFile(image.getOriginalFilename(), image.getBytes());
            existing.setImageUrl(imageUrl);
        }
        existing.setTitle(project.getTitle());
        existing.setDescription(project.getDescription());
        existing.setLiveLink(project.getLiveLink());
        existing.setGithubLink(project.getGithubLink());
        return projectRepo.save(existing);
    }

    public void deleteProject(String id) throws Exception {
        Project project = getProject(id);
        if (project.getImageUrl() != null) deleteFile(project.getImageUrl());
        projectRepo.delete(project);
    }
}