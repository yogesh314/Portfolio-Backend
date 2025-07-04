package com.yogesh.Portfolio_Backend.service;

import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class BaseService {
    protected final S3Client s3Client;
    @Value("${aws.s3.bucket}")
    protected String bucketName;

    protected BaseService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    protected String uploadFile(String fileName, byte[] fileData) {
        String key = UUID.randomUUID().toString() + "_" + fileName;

        //Map file extensions to Content-Types
        String contentType = getContentType(fileName);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType) // Set the Content-Type
                .build();

        s3Client.putObject(putObjectRequest, software.amazon.awssdk.core.sync.RequestBody.fromBytes(fileData));
        return s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(key)).toString();
    }

    private static String getContentType(String fileName) {
        Map<String, String> contentTypes = new HashMap<>();
        contentTypes.put(".jpg", "image/jpeg");
        contentTypes.put(".jpeg", "image/jpeg");
        contentTypes.put(".png", "image/png");
        contentTypes.put(".pdf", "application/pdf");

        return contentTypes.getOrDefault(
                fileName.substring(fileName.lastIndexOf(".")).toLowerCase(),
                "application/octet-stream" // Default if extension not recognized
        );
    }

    protected void deleteFile(String url) {
        String key = url.substring(url.lastIndexOf("/") + 1);
        s3Client.deleteObject(b -> b.bucket(bucketName).key(key));
    }
}