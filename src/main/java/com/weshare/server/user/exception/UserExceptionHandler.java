package com.weshare.server.user.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<UserErrorResponse> userExceptionErrorHandler(UserException exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(new UserErrorResponse(exception));
    }
}