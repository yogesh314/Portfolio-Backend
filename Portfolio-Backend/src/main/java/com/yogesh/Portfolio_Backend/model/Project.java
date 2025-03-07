package com.yogesh.Portfolio_Backend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Project extends BaseEntity {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    private String imageUrl;
    private String liveLink;
    private String githubLink;
}