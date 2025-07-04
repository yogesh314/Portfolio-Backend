package com.yogesh.Portfolio_Backend.service.blog_service;

import com.yogesh.Portfolio_Backend.model.documents.blog.Blog;
import com.yogesh.Portfolio_Backend.repository.blog_repository.BlogRepo;
import com.yogesh.Portfolio_Backend.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class BlogService extends BaseService {
    private final BlogRepo blogRepo;

    public BlogService(BlogRepo blogRepo, S3Client s3Client) {
        super(s3Client);
        this.blogRepo = blogRepo;
    }

    public Blog createBlog(Blog blog, MultipartFile image) throws Exception {
        if (image != null && !image.isEmpty()) {
            String imageUrl = uploadFile(image.getOriginalFilename(), image.getBytes());
            blog.setImageUrl(imageUrl);
        }
        blog.setId(UUID.randomUUID().toString());
        return blogRepo.save(blog);
    }

    public Blog getBlog(String id) throws Exception {
        Blog blog = blogRepo.findById(id);
        if (blog == null) throw new NoSuchElementException("Blog not found: " + id);
        return blog;
    }

    public List<Blog> getAllBlogs() throws Exception {
        return blogRepo.findAll();
    }

    public Blog updateBlog(String id, Blog blog, MultipartFile image) throws Exception {
        Blog existing = getBlog(id);
        if (image != null && !image.isEmpty()) {
            if (existing.getImageUrl() != null) deleteFile(existing.getImageUrl());
            String imageUrl = uploadFile(image.getOriginalFilename(), image.getBytes());
            existing.setImageUrl(imageUrl);
        }
        existing.setTitle(blog.getTitle());
        existing.setDate(blog.getDate());
        existing.setContent(blog.getContent());
        existing.setTechStack(blog.getTechStack());
        return blogRepo.save(existing);
    }

    public void deleteBlog(String id) throws Exception {
        Blog blog = getBlog(id);
        if (blog.getImageUrl() != null) deleteFile(blog.getImageUrl());
        blogRepo.delete(blog);
    }
}
