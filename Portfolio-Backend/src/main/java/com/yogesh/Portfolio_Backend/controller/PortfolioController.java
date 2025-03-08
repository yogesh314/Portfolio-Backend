package com.yogesh.Portfolio_Backend.controller;

import com.yogesh.Portfolio_Backend.model.Project;
import com.yogesh.Portfolio_Backend.service.ProjectService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PortfolioController {
    private final ProjectService projectService;

    public PortfolioController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping(value = "/projects")
    public ResponseEntity<Project> createProject(
            @RequestPart("project") Project project,
            @RequestPart("image") MultipartFile image) throws Exception {
        Project savedProject = projectService.createProject(project, image);
        return ResponseEntity.ok(savedProject);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable String id) throws Exception {
        Project project = projectService.getProject(id);
        return project != null ? ResponseEntity.ok(project) : ResponseEntity.notFound().build();
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() throws Exception {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable String id,
            @RequestPart("project") Project project,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        Project updatedProject = projectService.updateProject(id, project, image);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) throws Exception {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }
}