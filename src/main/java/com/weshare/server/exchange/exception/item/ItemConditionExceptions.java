package com.weshare.server.exchange.exception.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ItemConditionExceptions {
    ITEM_CONDITION_STRING_TO_ENUM_ERROR(HttpStatus.BAD_REQUEST,"STRING_TO_ENUM_ERROR","존재하지 않는 상태 키워드 입니다.");
    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
