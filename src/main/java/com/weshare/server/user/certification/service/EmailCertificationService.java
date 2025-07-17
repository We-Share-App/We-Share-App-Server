package com.weshare.server.user.certification.service;

import com.weshare.server.user.certification.entity.UserEmailCertification;

public interface EmailCertificationService {
    UserEmailCertification createCertificationKey(String emailAddress); // 이메일에 따른 인증키 등록 메서드
    void sendCertificationEmail(String emailAddress); // 인증 번호 이메일 전송 메서드
    void doCertificateEmail(String emailAddress, String key); // 이메일에 대한 인증성공 여부 업데이트 메서드
}
