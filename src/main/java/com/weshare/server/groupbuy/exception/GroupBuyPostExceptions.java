package com.weshare.server.groupbuy.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GroupBuyPostExceptions {
    EXPIRATION_DATE_EARLY_ERROR(HttpStatus.BAD_REQUEST,"EXPIRATION_DATE_ERROR-401","모집 마감일이 금일보다 빠를수는 없습니다."),
    ;
    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
