package com.weshare.server.exchange.proposal.exception;

import com.weshare.server.exchange.exception.post.ExchangePostErrorResponse;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.proposal.entity.ExchangeProposal;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(1)
public class ExchangeProposalErrorHandler {
    @ExceptionHandler(ExchangeProposalException.class)
    public ResponseEntity<ExchangeProposalErrorResponse> exchangeProposalErrorHandler(ExchangeProposalException exception){
        return ResponseEntity.status(exception.getHttpStatus()).body(new ExchangeProposalErrorResponse(exception));
    }
}
