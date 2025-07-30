package com.weshare.server.location.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class LocationErrorHandler {
    @ExceptionHandler(LocationException.class)
    public ResponseEntity<LocationErrorResponse> locationErrorHandler(LocationException exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(new LocationErrorResponse(exception));
    }
}
