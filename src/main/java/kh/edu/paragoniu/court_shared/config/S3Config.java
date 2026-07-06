package kh.edu.paragoniu.court_shared.config;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class S3Config {

    @Value("${spring.storage.s3.access-key}")
    private String accessKey;

    @Value("${spring.storage.s3.secret-key}")
    private String secretKey;

    @Value("${spring.storage.s3.endpoint}")
    private String endpoint;

    @Value("${spring.storage.s3.region}")
    private String region;

    @Value("${spring.storage.s3.bucket-name}")
    private String bucketName;

    @Value("${spring.storage.s3.public-url}")
    private String publicUrl;

    public String getEndpoint() {
        return endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getPublicUrl() {
        return publicUrl;
    }

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
            accessKey,
            secretKey
        );

        return S3Client.builder()
            .endpointOverride(URI.create(endpoint))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .region(Region.of(region.toLowerCase().trim()))
            .serviceConfiguration(
                S3Configuration.builder().pathStyleAccessEnabled(true).build()
            )
            .build();
    }
}
