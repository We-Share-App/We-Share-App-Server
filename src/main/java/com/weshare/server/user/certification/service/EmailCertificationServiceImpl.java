package com.weshare.server.user.certification.service;

import com.weshare.server.user.certification.entity.UserEmailCertification;
import com.weshare.server.user.certification.exception.EmailCertificationException;
import com.weshare.server.user.certification.exception.EmailCertificationExceptions;
import com.weshare.server.user.certification.repository.UserEmailCertificationRepository;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailCertificationServiceImpl implements EmailCertificationService{
    private final UserEmailCertificationRepository userEmailCertificationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String senderEmailAddress;

    @Override
    @Transactional
    public UserEmailCertification createCertificationKey(String emailAddress) {

        String certificationKey = generateRandomString(6); // 6자리 인증번호 생성

        // 이미 이메일 인증번호가 존재하는 경우에는 인증 번호를 변경함
        if(userEmailCertificationRepository.existsByEmailAddress(emailAddress)){
            UserEmailCertification userEmailCertification = userEmailCertificationRepository.findByEmailAddress(emailAddress)
                    .orElseThrow(()-> new EmailCertificationException(EmailCertificationExceptions.User_EMAIL_CERTIFICATION_NOT_FOUND));
            userEmailCertification.changeCertificationKey(certificationKey);
            userEmailCertification.changeExpireDateTime(LocalDateTime.now().plusMinutes(3));
            return  userEmailCertification;
        }

        // 최초 인증 시도인 경우 -> UserEmailCertification 객체 생성및 DB 저장
        UserEmailCertification userEmailCertification = UserEmailCertification.builder()
                .emailAddress(emailAddress)
                .certificationKey(certificationKey)
                .expirationDateTime(LocalDateTime.now().plusMinutes(3))
                .build();

        userEmailCertificationRepository.save(userEmailCertification);
        return  userEmailCertification;
    }

    // 이메일 인증번호 발송 메서드
    @Override
    @Async
    public void sendCertificationEmail(String emailAddress) {
        UserEmailCertification userEmailCertification = userEmailCertificationRepository.findByEmailAddress(emailAddress)
                .orElseThrow(()-> new EmailCertificationException(EmailCertificationExceptions.User_EMAIL_CERTIFICATION_NOT_FOUND));
        String certificationKey = userEmailCertification.getCertificationKey();
        sendMail(emailAddress,certificationKey);
    }

    // 이메일 인증시도 메서드
    @Override
    @Transactional
    public void doCertificateEmail(String emailAddress, String key) {
        UserEmailCertification userEmailCertification = userEmailCertificationRepository.findByEmailAddress(emailAddress)
                .orElseThrow(()-> new EmailCertificationException(EmailCertificationExceptions.User_EMAIL_CERTIFICATION_NOT_FOUND));

        if(userEmailCertification.getCertificationKey().equals(key)){

            // 만료된 이메일 인증 번호일 경우
            if(userEmailCertification.getExpirationDateTime().isBefore(LocalDateTime.now())){
                throw new EmailCertificationException(EmailCertificationExceptions.USER_EMAIL_CERTIFICATION_EXPIRED);
            }

            User user = userRepository.findByEmail(emailAddress).orElseThrow(()->new UserException(UserExceptions.USER_EMAIL_NOT_FOUND));
            user.changeIsVerified(true); // 유저 엔티티의 is_certificated 필드를 true로 변경함
            log.info("[EMAIL_CERTIFICATION_KEY_VALIDATION_SUCCESS] email_address : {}",user.getEmail());
        }
        else {
            throw new EmailCertificationException(EmailCertificationExceptions.USER_EMAIL_CERTIFICATION_FAILURE);
        }
    }

    private String generateRandomString(int length){
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "#_-";

        // 모든 가능한 문자를 하나의 문자열로 결합
        String allChars = upperCaseLetters + lowerCaseLetters + specialCharacters;

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // length 길이만큼 난수 문자열 생성
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(allChars.length());
            sb.append(allChars.charAt(index));
        }
        return sb.toString();
    }

    private void sendMail(String emailAddress, String certificationKey){

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(senderEmailAddress);
            helper.setTo(emailAddress);
            helper.setSubject("[We share 이메일 인증번호]");

            String htmlContent = "<html>" +
                    "<body style='background-color: #cccccc !important; margin: 0; padding: 20px;'>" +
                    "<div style='max-width: 600px; margin: auto; background-color: #2a475e; padding: 20px; border-radius: 8px; color: #c7d5e0; font-family: Arial, sans-serif;'>" +
                    "<h2 style='margin-top: 0;'>이메일 인증</h2>" +
                    "<p>아래 인증 코드를 입력하여 이메일 인증을 완료하세요.</p>" +
                    "<p>" +
                    "<strong style='font-size: 20px;'>인증코드:</strong><br>" +
                    "<span style='font-size: 18px; background-color: #FF8C00; color: #ffffff; padding: 15px 25px; border-radius: 5px; display: inline-block; margin: 5px 0;'>" +
                    certificationKey +
                    "</span>" +
                    "</p>" +
                    "<p style='margin-top: 20px; margin-bottom: 20px;'>유효시간: 3분</p>" +
                    "<p>이 메일은 계정 보안을 위해 발송되었습니다.</p>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(htmlContent, true);
            mailSender.send(message);
            log.info("[EMAIL_CERTIFICATION_KEY_SEND] email_address : {}",emailAddress);
        }catch (MessagingException e){
            throw new EmailCertificationException(EmailCertificationExceptions.User_EMAIL_CERTIFICATION_SEND_ERROR);
        }

    }
}
