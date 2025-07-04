package com.yogesh.Portfolio_Backend.repository.contact_submission_repository;

import com.yogesh.Portfolio_Backend.model.documents.contact_submission.ContactSubmission;
import com.yogesh.Portfolio_Backend.repository.DynamoDBRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactSubmissionRepo {
    private final DynamoDBRepository<ContactSubmission> dynamoDBRepository;

    public ContactSubmissionRepo(DynamoDBRepository<ContactSubmission> dynamoDBRepository) {
        this.dynamoDBRepository = dynamoDBRepository;
    }

    public ContactSubmission save(ContactSubmission submission) throws Exception {
        return dynamoDBRepository.save(submission);
    }

    public ContactSubmission findById(String id) throws Exception {
        return dynamoDBRepository.findById(id, ContactSubmission.class);
    }

    public List<ContactSubmission> findAll() throws Exception {
        return dynamoDBRepository.findAll(ContactSubmission.class);
    }

    public void delete(ContactSubmission submission) throws Exception {
        dynamoDBRepository.delete(submission);
    }
}
