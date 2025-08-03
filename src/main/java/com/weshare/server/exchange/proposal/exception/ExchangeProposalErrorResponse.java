package com.weshare.server.exchange.proposal.exception;

import com.weshare.server.common.exception.base.BaseErrorResponse;

public class ExchangeProposalErrorResponse extends BaseErrorResponse {
    public ExchangeProposalErrorResponse(ExchangeProposalException exception){
        super(exception.getHttpStatus(),exception.getErrorCode(),exception.getMessage());
    }
}
