package com.yogesh.Portfolio_Backend.repository.resume_repository;

import com.yogesh.Portfolio_Backend.model.documents.resume.Resume;
import com.yogesh.Portfolio_Backend.repository.DynamoDBRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResumeRepo {
    private final DynamoDBRepository<Resume> dynamoDBRepository;

    public ResumeRepo(DynamoDBRepository<Resume> dynamoDBRepository) {
        this.dynamoDBRepository = dynamoDBRepository;
    }

    public Resume save(Resume resume) throws Exception {
        return dynamoDBRepository.save(resume);
    }

    public Resume findById(String id) throws Exception {
        return dynamoDBRepository.findById(id, Resume.class);
    }

    public List<Resume> findAll() throws Exception {
        return dynamoDBRepository.findAll(Resume.class);
    }

    public void delete(Resume resume) throws Exception {
        dynamoDBRepository.delete(resume);
    }

}
