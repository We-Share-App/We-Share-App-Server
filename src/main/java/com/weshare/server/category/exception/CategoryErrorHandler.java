package com.weshare.server.category.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class CategoryErrorHandler {
    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<CategoryErrorResponse> categoryErrorHandler(CategoryException exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(new CategoryErrorResponse(exception));
    }
}
