package com.yogesh.Portfolio_Backend.controller;

import com.yogesh.Portfolio_Backend.model.documents.blog.Blog;
import com.yogesh.Portfolio_Backend.model.documents.contact_submission.ContactSubmission;
import com.yogesh.Portfolio_Backend.model.documents.project.Project;
import com.yogesh.Portfolio_Backend.model.documents.resume.Resume;
import com.yogesh.Portfolio_Backend.service.blog_service.BlogService;
import com.yogesh.Portfolio_Backend.service.contact_submission_service.ContactSubmissionService;
import com.yogesh.Portfolio_Backend.service.project_service.ProjectService;
import com.yogesh.Portfolio_Backend.service.resume_service.ResumeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PortfolioController {
    private final ProjectService projectService;
    private final BlogService blogService;
    private final ResumeService resumeService;
    private final ContactSubmissionService contactSubmissionService;

    public PortfolioController(ProjectService projectService, BlogService blogService, ResumeService resumeService, ContactSubmissionService contactSubmissionService) {
        this.projectService = projectService;
        this.blogService = blogService;
        this.resumeService = resumeService;
        this.contactSubmissionService = contactSubmissionService;
    }

    // Project Endpoints
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

    // Blog Endpoints
    @PostMapping("/blogs")
    public ResponseEntity<Blog> createBlog(
            @RequestPart("blog") Blog blog,
            @RequestPart("image") MultipartFile image) throws Exception {
        Blog savedBlog = blogService.createBlog(blog, image);
        return ResponseEntity.ok(savedBlog);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blog> getBlog(@PathVariable String id) throws Exception {
        Blog blog = blogService.getBlog(id);
        return blog != null ? ResponseEntity.ok(blog) : ResponseEntity.notFound().build();
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<Blog>> getAllBlogs() throws Exception {
        return ResponseEntity.ok(blogService.getAllBlogs());
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<Blog> updateBlog(
            @PathVariable String id,
            @RequestPart("blog") Blog blog,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        Blog updatedBlog = blogService.updateBlog(id, blog, image);
        return ResponseEntity.ok(updatedBlog);
    }

    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable String id) throws Exception {
        blogService.deleteBlog(id);
        return ResponseEntity.ok().build();
    }

    // Resume Endpoints
    @PostMapping("/resumes")
    public ResponseEntity<Resume> createResume(
            @RequestPart("resume") Resume resume,
            @RequestPart("pdf") MultipartFile pdf) throws Exception {
        Resume savedResume = resumeService.createResume(resume, pdf);
        return ResponseEntity.ok(savedResume);
    }

    @GetMapping("/resumes/{id}")
    public ResponseEntity<Resume> getResume(@PathVariable String id) throws Exception {
        Resume resume = resumeService.getResume(id);
        return resume != null ? ResponseEntity.ok(resume) : ResponseEntity.notFound().build();
    }

    @GetMapping("/resumes")
    public ResponseEntity<List<Resume>> getAllResumes() throws Exception {
        return ResponseEntity.ok(resumeService.getAllResumes());
    }

    @PutMapping("/resumes/{id}")
    public ResponseEntity<Resume> updateResume(
            @PathVariable String id,
            @RequestPart("resume") Resume resume,
            @RequestPart(value = "pdf", required = false) MultipartFile pdf) throws Exception {
        Resume updatedResume = resumeService.updateResume(id, resume, pdf);
        return ResponseEntity.ok(updatedResume);
    }

    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable String id) throws Exception {
        resumeService.deleteResume(id);
        return ResponseEntity.ok().build();
    }

    // ContactSubmission Endpoints
    @PostMapping("/contact-submissions")
    public ResponseEntity<ContactSubmission> createContactSubmission(@RequestBody ContactSubmission submission) throws Exception {
        ContactSubmission savedSubmission = contactSubmissionService.createContactSubmission(submission);
        return ResponseEntity.ok(savedSubmission);
    }

    @GetMapping("/contact-submissions/{id}")
    public ResponseEntity<ContactSubmission> getContactSubmission(@PathVariable String id) throws Exception {
        ContactSubmission submission = contactSubmissionService.getContactSubmission(id);
        return submission != null ? ResponseEntity.ok(submission) : ResponseEntity.notFound().build();
    }

    @GetMapping("/contact-submissions")
    public ResponseEntity<List<ContactSubmission>> getAllContactSubmissions() throws Exception {
        return ResponseEntity.ok(contactSubmissionService.getAllContactSubmissions());
    }

    @DeleteMapping("/contact-submissions/{id}")
    public ResponseEntity<Void> deleteContactSubmission(@PathVariable String id) throws Exception {
        contactSubmissionService.deleteContactSubmission(id);
        return ResponseEntity.ok().build();
    }
}