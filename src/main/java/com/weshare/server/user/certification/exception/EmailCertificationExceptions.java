package com.weshare.server.user.certification.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EmailCertificationExceptions {
    User_EMAIL_CERTIFICATION_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"USER-EMAIL-502","이메일 인증키 전송실패"),
    User_EMAIL_CERTIFICATION_NOT_FOUND(HttpStatus.BAD_REQUEST,"USER-EMAIL-404","인증 등록되지 않은 이메일 주소"),
    USER_EMAIL_CERTIFICATION_FAILURE(HttpStatus.BAD_REQUEST,"USER-EMAIL-401","이메일 주소 인증 실패"),
    USER_EMAIL_CERTIFICATION_EXPIRED(HttpStatus.BAD_REQUEST,"USER-EMAIL-401","만료된 이메일 인증")
    ;

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
