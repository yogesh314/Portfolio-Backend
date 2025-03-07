package com.yogesh.Portfolio_Backend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Blog extends BaseEntity {
    @NotBlank(message = "Title is required")
    private String title;
    private String date;
    private String content;
    private String[] techStack;
    private String imageUrl;
}