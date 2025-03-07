package com.yogesh.Portfolio_Backend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public abstract class BaseEntity {
    @NotBlank(message = "ID is required")
    private String id;
}
