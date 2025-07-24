package com.weshare.server.aws.s3.service;


import com.weshare.server.aws.s3.exception.S3Exception;
import com.weshare.server.aws.s3.exception.S3Exceptions;
import com.weshare.server.aws.s3.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import javax.imageio.IIOException;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@Slf4j
public class S3ServiceImpl implements S3Service {
    private final S3Client s3Client;
    private final String bucketName;
    private final String accessKey;
    private final String secretKey;
    private final String region;

    public S3ServiceImpl(@Value("${aws.credentials.access-key}") String accessKey, @Value("${aws.credentials.secret-key}") String secretKey, @Value("${aws.region}") String region, @Value("${aws.s3.bucket-name}") String bucketName) {
        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
        this.bucketName = bucketName;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
    }
    @Override
    // 이미지 -> s3 업로드 메서드
    public String uploadImage(String directory, MultipartFile file) {

        // 이미지 확장자 추출
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);

        if (!isValidImageExtension(fileExtension)) {
            // 지원되지 않는 이미지 확장자인 경우
            throw new S3Exception(S3Exceptions.IMAGE_EXTENSION_NOT_SUPPORTED);
        }

        // 이미지 접근 키 : "디렉토리명/" + "UUID" + "." + "이미지 확장자명"
        String key = directory + "/" + UUID.randomUUID() + "." + fileExtension;

        //s3 업로드 request 객체
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())  // 올바른 Content-Type 지정
                .build();

        try { s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e){
            throw new S3Exception(S3Exceptions.IMAGE_SERIALIZE_ERROR); // 직렬화 예외 발생
        }
        // key 값 리턴
        log.info("[s3 이미지 업로드 성공] key :{}",key);
        return key;
    }

    @Override
    public String getPresignedUrl(String key) {
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(5)) // 유효기간 5분 설정
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(presignRequest);
            log.info("[s3 이미지 presigned URL 생성 성공] url :{}",presignedRequest.url().toString());
            return presignedRequest.url().toString();
        } finally {
            presigner.close();
        }
    }

    // 확장자 유효성 검사
    private boolean isValidImageExtension(String extension) {
        return extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("jpg") ||
                extension.equalsIgnoreCase("jpeg") ||
                extension.equalsIgnoreCase("gif") ||
                extension.equalsIgnoreCase("bmp");
    }

    // 파일 확장자 추출
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
