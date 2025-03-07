package com.yogesh.Portfolio_Backend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Resume extends BaseEntity {
    @NotBlank(message = "Content is required")
    private String content;
    private String pdfUrl;
}