package com.weshare.server.exchange.proposal.exception;

import com.weshare.server.common.exception.base.BaseException;
import lombok.Getter;

@Getter
public class ExchangeProposalException extends BaseException {
    public ExchangeProposalException(ExchangeProposalExceptions exceptions){
        super(exceptions.getErrorType(),exceptions.getErrorCode(), exceptions.getMessage());
    }
}
