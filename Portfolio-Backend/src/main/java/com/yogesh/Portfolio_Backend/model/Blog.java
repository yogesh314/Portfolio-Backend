package com.yogesh.Portfolio_Backend.model;

import lombok.Data;

@Data
public class Blog {
    private String id;
    private String title;
    private String date;
    private String content;
    private String[] techStack;
    private String imageUrl; // S3 URL for blog image
}