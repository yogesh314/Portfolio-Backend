package com.yogesh.Portfolio_Backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    private static final Logger logger = LoggerFactory.getLogger(AwsConfig.class);

    @Value("${aws.region}")
    private String region;

    @Bean
    public DynamoDbClient dynamoDbClient() {
        logger.info("Initializing DynamoDbClient for region: {}", region);
        return DynamoDbClient.builder()
                .region(Region.of(region))
                .build(); // Uses default credential provider chain
    }

    @Bean
    public S3Client s3Client() {
        logger.info("Initializing S3Client for region: {}", region);
        return S3Client.builder()
                .region(Region.of(region))
                .build(); // Uses default credential provider chain
    }
}