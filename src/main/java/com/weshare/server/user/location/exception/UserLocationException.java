package com.weshare.server.user.location.exception;

import com.weshare.server.common.exception.base.BaseException;
import lombok.Getter;

@Getter
public class UserLocationException extends BaseException {
    public UserLocationException(UserLocationExceptions exceptions){
        super(exceptions.getErrorType(),exceptions.getErrorCode(), exceptions.getMessage());
    }
}
