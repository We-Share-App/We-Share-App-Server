package com.weshare.server.user.location.exception;

import com.weshare.server.common.exception.base.BaseErrorResponse;
import com.weshare.server.user.location.entity.UserLocation;
import lombok.Getter;

@Getter
public class UserLocationErrorResponse extends BaseErrorResponse {
    public UserLocationErrorResponse(UserLocationException exception){
        super(exception.getHttpStatus(),exception.getErrorCode(),exception.getMessage());
    }
}
