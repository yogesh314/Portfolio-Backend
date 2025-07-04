package com.yogesh.Portfolio_Backend.service.contact_submission_service;

import com.yogesh.Portfolio_Backend.model.documents.contact_submission.ContactSubmission;
import com.yogesh.Portfolio_Backend.repository.contact_submission_repository.ContactSubmissionRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ContactSubmissionService {
    private final ContactSubmissionRepo contactSubmissionRepo;

    public ContactSubmissionService(ContactSubmissionRepo contactSubmissionRepo) {
        this.contactSubmissionRepo = contactSubmissionRepo;
    }

    public ContactSubmission createContactSubmission(ContactSubmission submission) throws Exception {
        submission.setId(UUID.randomUUID().toString());
        return contactSubmissionRepo.save(submission);
    }

    public ContactSubmission getContactSubmission(String id) throws Exception {
        ContactSubmission submission = contactSubmissionRepo.findById(id);
        if (submission == null) throw new NoSuchElementException("ContactSubmission not found: " + id);
        return submission;
    }

    public List<ContactSubmission> getAllContactSubmissions() throws Exception {
        return contactSubmissionRepo.findAll();
    }

    public void deleteContactSubmission(String id) throws Exception {
        ContactSubmission submission = getContactSubmission(id);
        contactSubmissionRepo.delete(submission);
    }
}
