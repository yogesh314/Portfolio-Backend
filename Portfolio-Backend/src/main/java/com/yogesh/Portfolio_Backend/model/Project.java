package com.yogesh.Portfolio_Backend.model;

import lombok.Data;

@Data
public class Project {
    private String id;
    private String title;
    private String description;
    private String imageUrl; // S3 URL for project image
    private String liveLink;
    private String githubLink;
}
