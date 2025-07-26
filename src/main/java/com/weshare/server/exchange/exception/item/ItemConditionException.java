package com.weshare.server.exchange.exception.item;

import com.weshare.server.common.exception.base.BaseException;
import lombok.Getter;

@Getter
public class ItemConditionException extends BaseException {
     public ItemConditionException(ItemConditionExceptions exceptions){
          super(exceptions.getErrorType(),exceptions.getErrorCode(), exceptions.getMessage());
     }
}
