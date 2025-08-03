package com.weshare.server.exchange.candidate.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class ExchangeCandidatePostErrorHandler {
    @ExceptionHandler(ExchangeCandidatePostException.class)
    public ResponseEntity<ExchangeCandidatePostErrorResponse> exchangeCandidateErrorHandler(ExchangeCandidatePostException exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(new ExchangeCandidatePostErrorResponse(exception));
    }
}
