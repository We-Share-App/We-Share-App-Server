package com.weshare.server.user.exception;

import com.weshare.server.common.exception.base.BaseErrorResponse;
import lombok.Getter;

@Getter
public class UserErrorResponse extends BaseErrorResponse {
    public UserErrorResponse(UserException exception){
        super(exception.getHttpStatus(),exception.getErrorCode(),exception.getMessage());
    }
}