package com.weshare.server.exchange.exception.post;

import com.weshare.server.common.exception.base.BaseException;
import lombok.Getter;

@Getter
public class ExchangePostException extends BaseException {

    public ExchangePostException(ExchangePostExceptions exceptions){
        super(exceptions.getErrorType(),exceptions.getErrorCode(), exceptions.getMessage());
    }

}
