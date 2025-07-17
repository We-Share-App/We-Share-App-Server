package com.weshare.server.user.certification.controller;

import com.weshare.server.user.certification.dto.EmailRequest;
import com.weshare.server.user.certification.dto.CertificationResponse;
import com.weshare.server.user.certification.dto.EmailVerificationRequest;
import com.weshare.server.user.certification.service.EmailCertificationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user/email/certification")
public class EmailCertificationController {

    private final EmailCertificationService emailCertificationService;

    // 사용자 이메일 인증요청 처리 (인증객체 생성 및 저장)
    @Operation(
            summary = "이메일 인증번호 생성 요청",
            description = "입력된 이메일에 대한 인증번호를 생성하여 DB에 저장함 (유효시간 3분)"
    )

    @PostMapping
    public ResponseEntity<CertificationResponse> sendEmailCertificationKey(@RequestBody EmailRequest dto){
        String emailAddress = dto.getEmail();
        log.info("[EMAIL_CERTIFICATION_KEY_REQUEST] email_address : {}",emailAddress);
        emailCertificationService.createCertificationKey(emailAddress);
        emailCertificationService.sendCertificationEmail(emailAddress);
        log.info("[EMAIL_CERTIFICATION_KEY_RESPONSE] email_address : {}",emailAddress);
        CertificationResponse response = new CertificationResponse(HttpStatus.OK,"email_certification_request-200","이메일 인증번호 생성 & 발송 성공");
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<CertificationResponse> verifyEmailCertificationKey(@RequestBody EmailVerificationRequest dto){
        log.info("[EMAIL_CERTIFICATION_KEY_VERIFY_REQUEST] email_address : {}",dto.getEmail());
        emailCertificationService.doCertificateEmail(dto.getEmail(),dto.getKey());
        CertificationResponse response = new CertificationResponse(HttpStatus.OK,"email_certification-200","이메일 인증 성공");
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
