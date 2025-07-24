package com.weshare.server.aws.s3.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class S3ExceptionHandler {

    @ExceptionHandler(S3Exception.class)
    public ResponseEntity<S3ErrorResponse> s3ExceptionErrorHandler(S3Exception exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(new S3ErrorResponse(exception));
    }

}
