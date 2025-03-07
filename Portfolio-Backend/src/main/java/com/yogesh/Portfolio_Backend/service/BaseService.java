package com.yogesh.Portfolio_Backend.service;

import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.beans.factory.annotation.Value;

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
        s3Client.putObject(r -> r.bucket(bucketName).key(key), software.amazon.awssdk.core.sync.RequestBody.fromBytes(fileData));
        return s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(key)).toString();
    }

    protected void deleteFile(String url) {
        String key = url.substring(url.lastIndexOf("/") + 1);
        s3Client.deleteObject(b -> b.bucket(bucketName).key(key));
    }
}