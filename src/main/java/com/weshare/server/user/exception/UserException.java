package com.weshare.server.user.exception;

import com.weshare.server.common.exception.base.BaseException;
import lombok.Getter;

@Getter
public class UserException extends BaseException {

    public UserException(UserExceptions exceptions){
        super(exceptions.getErrorType(),exceptions.getErrorCode(), exceptions.getMessage());
    }
}