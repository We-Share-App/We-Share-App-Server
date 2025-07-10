package com.weshare.server.common.exception.globalException;

import com.weshare.server.common.exception.base.BaseException;
import lombok.Getter;

@Getter
public class GlobalException extends BaseException {
    public GlobalException(GlobalExceptions globalExceptions) {
        super(globalExceptions.getErrorType(), globalExceptions.getErrorCode(),globalExceptions.getMessage());
    }
}