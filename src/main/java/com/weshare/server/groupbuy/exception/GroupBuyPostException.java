package com.weshare.server.groupbuy.exception;

import com.weshare.server.common.exception.base.BaseException;
import lombok.Getter;

@Getter
public class GroupBuyPostException extends BaseException {
    public GroupBuyPostException(GroupBuyPostExceptions exceptions){
        super(exceptions.getErrorType(),exceptions.getErrorCode(), exceptions.getMessage());
    }
}
