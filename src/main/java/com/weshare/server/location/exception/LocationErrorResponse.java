package com.weshare.server.location.exception;

import com.weshare.server.category.exception.CategoryException;
import com.weshare.server.common.exception.base.BaseErrorResponse;

public class LocationErrorResponse extends BaseErrorResponse {
    public LocationErrorResponse(LocationException exception){
        super(exception.getHttpStatus(),exception.getErrorCode(),exception.getMessage());
    }
}
