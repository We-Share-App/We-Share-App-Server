package com.weshare.server.exchange.exception.item;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class ItemConditionErrorHandler {
    @ExceptionHandler(ItemConditionException.class)
    public ResponseEntity<ItemConditionErrorResponse> itemConditionErrorHandler(ItemConditionException exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(new ItemConditionErrorResponse(exception));
    }
}
