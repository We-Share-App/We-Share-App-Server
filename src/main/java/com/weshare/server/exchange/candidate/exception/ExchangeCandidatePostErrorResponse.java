package com.weshare.server.exchange.candidate.exception;

import com.weshare.server.common.exception.base.BaseErrorResponse;

public class ExchangeCandidatePostErrorResponse extends BaseErrorResponse {
    public ExchangeCandidatePostErrorResponse(ExchangeCandidatePostException exception){
        super(exception.getHttpStatus(),exception.getErrorCode(),exception.getMessage());
    }
}
