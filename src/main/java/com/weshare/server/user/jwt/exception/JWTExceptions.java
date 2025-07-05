package com.weshare.server.user.jwt.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT) // 필드 값을 JSON으로 변환
public enum JWTExceptions {
    NO_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"NO_ACCESS_TOKEN-401","액세스 토큰이 존재하지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED,"EXPIRED_ACCESS_TOKEN-401","액세스 토큰이 만료되었습니다."),

    ;

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
