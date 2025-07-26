package com.weshare.server.category.exception;

import com.weshare.server.common.exception.base.BaseException;
import lombok.Getter;

@Getter
public class CategoryException extends BaseException {
    public CategoryException(CategoryExceptions exceptions){
        super(exceptions.getErrorType(),exceptions.getErrorCode(), exceptions.getMessage());
    }
}
