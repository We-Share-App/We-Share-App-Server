package com.weshare.server.exchange.candidate.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExchangeCandidatePostExceptions {
    NOT_EXIST_EXCHANGE_CANDIDATE_POST(HttpStatus.BAD_REQUEST,"NOT_EXIST_EXCHANGE_CANDIDATE_POST_ID-401","존재하지 않는 물품교환 후보품 게시글 입니다."),
    NOT_AVAILABLE_EXCHANGE_CANDIDATE_POST(HttpStatus.BAD_REQUEST,"NOT_AVAILABLE_EXCHANGE_CANDIDATE_POST","교환 진행이 불가능한 물품교환 후보품 게시글 입니다.");
    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
