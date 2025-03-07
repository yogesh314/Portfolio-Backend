package com.yogesh.Portfolio_Backend.model;

import lombok.Data;

@Data
public class ContactSubmission {
    private String id;
    private String name;
    private String email;
    private String message;
}