package com.weshare.server.location.exception;

import com.weshare.server.category.exception.CategoryExceptions;
import com.weshare.server.common.exception.base.BaseException;

public class LocationException extends BaseException {
    public LocationException(LocationExceptions exceptions){
        super(exceptions.getErrorType(),exceptions.getErrorCode(), exceptions.getMessage());
    }
}
