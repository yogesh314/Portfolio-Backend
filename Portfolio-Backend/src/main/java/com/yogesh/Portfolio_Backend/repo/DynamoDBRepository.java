package com.yogesh.Portfolio_Backend.repo;

import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import java.util.List;

public interface DynamoDBRepository<T> {
    T save(T entity) throws DynamoDbException;
    T findById(String id, Class<T> clazz) throws DynamoDbException;
    List<T> findAll(Class<T> clazz) throws DynamoDbException;
    void delete(T entity) throws DynamoDbException;
}