package com.weshare.server.user.location.exception;

import com.weshare.server.user.exception.UserErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class UserLocationExceptionHandler {
    @ExceptionHandler(UserLocationException.class)
    public ResponseEntity<UserLocationErrorResponse> userLocationExceptionHandler(UserLocationException exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(new UserLocationErrorResponse(exception));
    }
}
