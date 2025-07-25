package com.weshare.server.category.exception;

import com.weshare.server.common.exception.base.BaseErrorResponse;
import lombok.Getter;

@Getter
public class CategoryErrorResponse extends BaseErrorResponse {
    public CategoryErrorResponse(CategoryException exception){
        super(exception.getHttpStatus(),exception.getErrorCode(),exception.getMessage());
    }
}
