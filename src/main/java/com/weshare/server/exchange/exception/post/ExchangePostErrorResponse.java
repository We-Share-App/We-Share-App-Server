package com.weshare.server.exchange.exception.post;

import com.weshare.server.common.exception.base.BaseErrorResponse;

public class ExchangePostErrorResponse extends BaseErrorResponse {
    public ExchangePostErrorResponse(ExchangePostException exception){
        super(exception.getHttpStatus(),exception.getErrorCode(),exception.getMessage());
    }
}
