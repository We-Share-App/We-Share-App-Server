package com.weshare.server.aws.s3.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadImage(String directory,MultipartFile file);
    String getPresignedUrl(String key);
}
