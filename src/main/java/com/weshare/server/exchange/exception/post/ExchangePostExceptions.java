package com.weshare.server.exchange.exception.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExchangePostExceptions {
    NOT_EXIST_EXCHANGE_POST(HttpStatus.BAD_REQUEST,"NOT_EXIST_EXCHANGE_POST_ID","존재하지 않는 물품교환 게시글 입니다.");

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;

}
