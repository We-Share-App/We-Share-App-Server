package com.weshare.server.common.exception.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public abstract class BaseErrorResponse {
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    public BaseErrorResponse(BaseException baseException){
        this.httpStatus = baseException.getHttpStatus();
        this.errorCode = baseException.getErrorCode();
        this.message = baseException.getMessage();
    }
}