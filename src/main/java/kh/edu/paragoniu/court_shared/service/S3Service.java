package kh.edu.paragoniu.court_shared.service;

import java.util.UUID;
import kh.edu.paragoniu.court_shared.config.S3Config;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final S3Config s3Config;

    public S3Service(S3Client s3Client, S3Config s3Config) {
        this.s3Client = s3Client;
        this.s3Config = s3Config;
    }

    /**
     * Upload document and picture files
     * @param folder
     * @param originalFilename
     * @param bytes
     * @param contentType
     * @return
     */
    public String uploadFile(
        String folder,
        String originalFilename,
        byte[] bytes,
        String contentType
    ) {
        String fileExtension = originalFilename.contains(".")
            ? originalFilename.substring(originalFilename.lastIndexOf("."))
            : "";

        String storageKey = folder + "/" + UUID.randomUUID() + fileExtension;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(s3Config.getBucketName())
            .key(storageKey)
            .contentType(contentType)
            .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));
        return storageKey;
    }

    /**
     * Generate public access URL for browsers to access the resources
     * @param storageKey
     * @return
     */
    public String generatePublicUrl(String storageKey) {
        if (storageKey == null || storageKey.isBlank()) {
            return null;
        }

        String basePublicUrl = s3Config.getPublicUrl();
        if (basePublicUrl == null || basePublicUrl.isBlank()) {
            basePublicUrl =
                s3Config.getEndpoint() + "/" + s3Config.getBucketName();
        }

        basePublicUrl = basePublicUrl.endsWith("/")
            ? basePublicUrl
            : basePublicUrl + "/";
        String cleanStorageKey = storageKey.startsWith("/")
            ? storageKey.substring(1)
            : storageKey;

        return basePublicUrl + cleanStorageKey;
    }
}
