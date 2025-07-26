package com.weshare.server.aws.s3.controller;

import com.weshare.server.aws.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test/images")
public class ImageUploadTestController {
    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String s3Url = s3Service.uploadImage("test",file);
            return ResponseEntity.ok("파일 업로드 성공: " + s3Url);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("파일 업로드 실패: " + e.getMessage());
        }
    }

    @GetMapping("/presigned")
    public ResponseEntity<String> getPresignedUrl(@RequestParam("key") String key) {
        try {
            String presignedUrl = s3Service.getPresignedUrl(key);
            return ResponseEntity.ok(presignedUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("presigned URL 생성 실패: " + e.getMessage());
        }
    }
}
