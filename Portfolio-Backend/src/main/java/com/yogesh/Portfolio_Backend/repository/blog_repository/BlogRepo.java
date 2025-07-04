package com.yogesh.Portfolio_Backend.repository.blog_repository;

import com.yogesh.Portfolio_Backend.model.documents.blog.Blog;
import com.yogesh.Portfolio_Backend.repository.DynamoDBRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlogRepo {
    private final DynamoDBRepository<Blog> dynamoDBRepository;

    public BlogRepo(DynamoDBRepository<Blog> dynamoDBRepository) {
        this.dynamoDBRepository = dynamoDBRepository;
    }

    public Blog save(Blog blog) throws Exception {
        return dynamoDBRepository.save(blog);
    }

    public Blog findById(String id) throws Exception {
        return dynamoDBRepository.findById(id, Blog.class);
    }

    public List<Blog> findAll() throws Exception {
        return dynamoDBRepository.findAll(Blog.class);
    }

    public void delete(Blog blog) throws Exception {
        dynamoDBRepository.delete(blog);
    }
}
