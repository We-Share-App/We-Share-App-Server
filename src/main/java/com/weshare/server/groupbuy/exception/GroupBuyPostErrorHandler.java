package com.weshare.server.groupbuy.exception;

import com.weshare.server.exchange.exception.post.ExchangePostErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class GroupBuyPostErrorHandler {
    @ExceptionHandler(GroupBuyPostException.class)
    public ResponseEntity<GroupBuyPostErrorResponse> groupBuyPostErrorHandler(GroupBuyPostException exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(new GroupBuyPostErrorResponse(exception));
    }
}
