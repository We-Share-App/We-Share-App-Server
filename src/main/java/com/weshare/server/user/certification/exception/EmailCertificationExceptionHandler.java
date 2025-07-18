package com.weshare.server.user.certification.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Order(1)
public class EmailCertificationExceptionHandler {
    @ExceptionHandler(EmailCertificationException.class)
    public ResponseEntity<EmailCertificationErrorResponse> certificationErrorHandler(EmailCertificationException exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(new EmailCertificationErrorResponse(exception));
    }

}
