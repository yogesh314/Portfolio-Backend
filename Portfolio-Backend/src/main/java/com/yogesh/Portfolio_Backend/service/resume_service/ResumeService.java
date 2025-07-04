package com.yogesh.Portfolio_Backend.service.resume_service;

import com.yogesh.Portfolio_Backend.model.documents.resume.Resume;
import com.yogesh.Portfolio_Backend.repository.resume_repository.ResumeRepo;
import com.yogesh.Portfolio_Backend.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ResumeService extends BaseService {
    private final ResumeRepo resumeRepo;

    public ResumeService(ResumeRepo resumeRepo, S3Client s3Client) {
        super(s3Client);
        this.resumeRepo = resumeRepo;
    }

    public Resume createResume(Resume resume, MultipartFile pdf) throws Exception {
        if (pdf != null && !pdf.isEmpty()) {
            String pdfUrl = uploadFile(pdf.getOriginalFilename(), pdf.getBytes());
            resume.setPdfUrl(pdfUrl);
        }
        resume.setId(UUID.randomUUID().toString());
        return resumeRepo.save(resume);
    }

    public Resume getResume(String id) throws Exception {
        Resume resume = resumeRepo.findById(id);
        if (resume == null) throw new NoSuchElementException("Resume not found: " + id);
        return resume;
    }

    public List<Resume> getAllResumes() throws Exception {
        return resumeRepo.findAll();
    }

    public Resume updateResume(String id, Resume resume, MultipartFile pdf) throws Exception {
        Resume existing = getResume(id);
        if (pdf != null && !pdf.isEmpty()) {
            if (existing.getPdfUrl() != null) deleteFile(existing.getPdfUrl());
            String pdfUrl = uploadFile(pdf.getOriginalFilename(), pdf.getBytes());
            existing.setPdfUrl(pdfUrl);
        }
        existing.setContent(resume.getContent());
        return resumeRepo.save(existing);
    }

    public void deleteResume(String id) throws Exception {
        Resume resume = getResume(id);
        if (resume.getPdfUrl() != null) deleteFile(resume.getPdfUrl());
        resumeRepo.delete(resume);
    }
}
