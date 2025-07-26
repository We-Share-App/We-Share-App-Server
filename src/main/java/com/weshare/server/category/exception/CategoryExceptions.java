package com.weshare.server.category.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CategoryExceptions {

    NOT_EXIST_CATEGORY_ID(HttpStatus.BAD_REQUEST,"NOT_EXIST_CATEGORY_ID","존재하지 않는 카테고리 입니다.");
    ;

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
