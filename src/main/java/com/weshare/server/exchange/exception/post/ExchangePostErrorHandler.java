package com.weshare.server.exchange.exception.post;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class ExchangePostErrorHandler {
    @ExceptionHandler(ExchangePostException.class)
    public ResponseEntity<ExchangePostErrorResponse> exchangePostErrorHandler(ExchangePostException exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(new ExchangePostErrorResponse(exception));
    }
}
