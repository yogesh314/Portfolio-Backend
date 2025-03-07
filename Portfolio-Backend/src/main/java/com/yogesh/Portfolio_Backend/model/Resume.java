package com.yogesh.Portfolio_Backend.model;

import lombok.Data;

@Data
public class Resume {
    private String id;
    private String content; // HTML or text content
    private String pdfUrl; // S3 URL for resume PDF
}