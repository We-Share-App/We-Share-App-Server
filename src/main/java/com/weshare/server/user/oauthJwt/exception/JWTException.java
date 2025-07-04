package com.weshare.server.user.oauthJwt.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class JWTException extends AuthenticationException {
    private final HttpStatus httpStatus;
    private final String errorCode;

    public JWTException(JWTExceptions exceptions){
        super(exceptions.getMessage());
        this.httpStatus = exceptions.getErrorType();
        this.errorCode = exceptions.getErrorCode();
    }
}
