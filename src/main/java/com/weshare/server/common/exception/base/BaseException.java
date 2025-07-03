package com.weshare.server.common.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public  abstract class BaseException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String errorCode;

    public BaseException(HttpStatus httpStatus, String errorCode, String message){
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}