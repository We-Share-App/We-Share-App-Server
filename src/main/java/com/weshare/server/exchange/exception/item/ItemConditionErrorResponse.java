package com.weshare.server.exchange.exception.item;

import com.weshare.server.common.exception.base.BaseErrorResponse;
import lombok.Getter;

@Getter
public class ItemConditionErrorResponse extends BaseErrorResponse {
    public ItemConditionErrorResponse(ItemConditionException exception){
        super(exception.getHttpStatus(),exception.getErrorCode(),exception.getMessage());
    }
}
