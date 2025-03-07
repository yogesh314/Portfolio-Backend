package com.yogesh.Portfolio_Backend.repo;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yogesh.Portfolio_Backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DynamoDBRepoImpl<T> implements DynamoDBRepository<T> {
    @Autowired
    private DynamoDbClient dynamoDbClient;

    @Autowired
    private ObjectMapper objectMapper; // For JSON mapping (included in Spring Boot)

    private final Map<Class<?>, String> tableNames = new HashMap<>();

    public DynamoDBRepoImpl() {
        tableNames.put(Project.class, "Projects");
        tableNames.put(Blog.class, "Blogs");
        tableNames.put(Resume.class, "Resume");
        tableNames.put(ContactSubmission.class, "ContactSubmissions");
    }

    @Override
    public T save(T entity) throws DynamoDbException {
        String tableName = getTableName(entity.getClass());
        Map<String, AttributeValue> item = convertToAttributeMap(entity);

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
        return entity;
    }

    @Override
    public T findById(String id, Class<T> clazz) throws DynamoDbException {
        String tableName = getTableName(clazz);
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(id).build());

        GetItemRequest request = GetItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build();

        Map<String, AttributeValue> result = dynamoDbClient.getItem(request).item();
        if (result == null || result.isEmpty()) {
            return null;
        }
        return convertFromAttributeMap(result, clazz);
    }

    @Override
    public List<T> findAll(Class<T> clazz) throws DynamoDbException {
        String tableName = getTableName(clazz);
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableName)
                .build();

        ScanResponse response = dynamoDbClient.scan(scanRequest);
        return response.items().stream()
                .map(item -> convertFromAttributeMap(item, clazz))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(T entity) throws DynamoDbException {
        String tableName = getTableName(entity.getClass());
        Map<String, AttributeValue> key = new HashMap<>();
        key.put("id", AttributeValue.builder().s(((BaseEntity) entity).getId()).build());

        DeleteItemRequest request = DeleteItemRequest.builder()
                .tableName(tableName)
                .key(key)
                .build();

        dynamoDbClient.deleteItem(request);
    }

    private String getTableName(Class<?> clazz) {
        return tableNames.getOrDefault(clazz, clazz.getSimpleName());
    }

    private Map<String, AttributeValue> convertToAttributeMap(T entity) {
        Map<String, AttributeValue> item = new HashMap<>();
        if (entity instanceof Project) {
            Project project = (Project) entity;
            item.put("id", AttributeValue.builder().s(project.getId()).build());
            item.put("title", AttributeValue.builder().s(project.getTitle()).build());
            item.put("description", AttributeValue.builder().s(project.getDescription() != null ? project.getDescription() : "").build());
            item.put("imageUrl", AttributeValue.builder().s(project.getImageUrl() != null ? project.getImageUrl() : "").build());
            item.put("liveLink", AttributeValue.builder().s(project.getLiveLink() != null ? project.getLiveLink() : "").build());
            item.put("githubLink", AttributeValue.builder().s(project.getGithubLink() != null ? project.getGithubLink() : "").build());
        } else if (entity instanceof Blog) {
            Blog blog = (Blog) entity;
            item.put("id", AttributeValue.builder().s(blog.getId()).build());
            item.put("title", AttributeValue.builder().s(blog.getTitle()).build());
            item.put("date", AttributeValue.builder().s(blog.getDate() != null ? blog.getDate() : "").build());
            item.put("content", AttributeValue.builder().s(blog.getContent() != null ? blog.getContent() : "").build());
            item.put("techStack", AttributeValue.builder().l(
                    blog.getTechStack() != null ? Arrays.stream(blog.getTechStack())
                            .map(value -> AttributeValue.builder().s(value).build())
                            .collect(Collectors.toList()) : new ArrayList<>()
            ).build());
            item.put("imageUrl", AttributeValue.builder().s(blog.getImageUrl() != null ? blog.getImageUrl() : "").build());
        } else if (entity instanceof Resume) {
            Resume resume = (Resume) entity;
            item.put("id", AttributeValue.builder().s(resume.getId()).build());
            item.put("content", AttributeValue.builder().s(resume.getContent()).build());
            item.put("pdfUrl", AttributeValue.builder().s(resume.getPdfUrl() != null ? resume.getPdfUrl() : "").build());
        } else if (entity instanceof ContactSubmission) {
            ContactSubmission submission = (ContactSubmission) entity;
            item.put("id", AttributeValue.builder().s(submission.getId()).build());
            item.put("name", AttributeValue.builder().s(submission.getName()).build());
            item.put("email", AttributeValue.builder().s(submission.getEmail()).build());
            item.put("message", AttributeValue.builder().s(submission.getMessage()).build());
        }
        return item;
    }

    @SuppressWarnings("unchecked")
    private T convertFromAttributeMap(Map<String, AttributeValue> item, Class<T> clazz) {
        try {
            if (clazz.equals(Project.class)) {
                Project project = new Project();
                project.setId(item.get("id").s());
                project.setTitle(item.get("title").s());
                project.setDescription(item.containsKey("description") ? item.get("description").s() : "");
                project.setImageUrl(item.containsKey("imageUrl") ? item.get("imageUrl").s() : "");
                project.setLiveLink(item.containsKey("liveLink") ? item.get("liveLink").s() : "");
                project.setGithubLink(item.containsKey("githubLink") ? item.get("githubLink").s() : "");
                return (T) project;
            } else if (clazz.equals(Blog.class)) {
                Blog blog = new Blog();
                blog.setId(item.get("id").s());
                blog.setTitle(item.get("title").s());
                blog.setDate(item.containsKey("date") ? item.get("date").s() : "");
                blog.setContent(item.containsKey("content") ? item.get("content").s() : "");
                blog.setTechStack(item.containsKey("techStack") ? item.get("techStack").l().stream()
                        .map(AttributeValue::s)
                        .toArray(String[]::new) : new String[0]);
                blog.setImageUrl(item.containsKey("imageUrl") ? item.get("imageUrl").s() : "");
                return (T) blog;
            } else if (clazz.equals(Resume.class)) {
                Resume resume = new Resume();
                resume.setId(item.get("id").s());
                resume.setContent(item.get("content").s());
                resume.setPdfUrl(item.containsKey("pdfUrl") ? item.get("pdfUrl").s() : "");
                return (T) resume;
            } else if (clazz.equals(ContactSubmission.class)) {
                ContactSubmission submission = new ContactSubmission();
                submission.setId(item.get("id").s());
                submission.setName(item.get("name").s());
                submission.setEmail(item.get("email").s());
                submission.setMessage(item.get("message").s());
                return (T) submission;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error converting item to object: " + e.getMessage(), e);
        }
        return null;
    }
}