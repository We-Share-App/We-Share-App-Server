package com.weshare.server.exchange.exception.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExchangePostExceptions {
    NOT_EXIST_EXCHANGE_POST(HttpStatus.BAD_REQUEST,"NOT_EXIST_EXCHANGE_POST_ID","존재하지 않는 물품교환 게시글 입니다."),
    NOT_EXIST_EXCHANGE_POST_ID(HttpStatus.BAD_REQUEST,"NOT_EXIST_EXCHANGE_POST_ID","존재하지 않는 물품교환 게시글로 id값을 조회할수 없습니다."),
    NOT_OPENED_EXCHANGE_POST(HttpStatus.BAD_REQUEST,"NOT_OPENED_EXCHANGE_POST-401","교환 진행이 불가능한 공개 물품교환 게시글 입니다.")
    ;

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;

}
