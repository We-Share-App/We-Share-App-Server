package com.weshare.server.exchange.candidate.exception;

import com.weshare.server.common.exception.base.BaseException;
import lombok.Getter;

@Getter
public class ExchangeCandidatePostException extends BaseException {
    public ExchangeCandidatePostException(ExchangeCandidatePostExceptions exceptions){
        super(exceptions.getErrorType(),exceptions.getErrorCode(), exceptions.getMessage());
    }
}
