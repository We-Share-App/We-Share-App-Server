package com.weshare.server.groupbuy.exception;

import com.weshare.server.common.exception.base.BaseErrorResponse;

public class GroupBuyPostErrorResponse extends BaseErrorResponse {
    public GroupBuyPostErrorResponse(GroupBuyPostException exception){
        super(exception.getHttpStatus(),exception.getErrorCode(),exception.getMessage());
    }
}
