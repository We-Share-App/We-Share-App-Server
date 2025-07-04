package com.weshare.server.user.oauthJwt.exception;

import com.weshare.server.common.exception.base.BaseErrorResponse;
import lombok.Getter;

@Getter
public class JWTErrorResponse extends BaseErrorResponse {
    public JWTErrorResponse(JWTException exception){
        super(exception.getHttpStatus(), exception.getErrorCode(), exception.getMessage());
    }
}
