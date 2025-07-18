package com.weshare.server.user.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserExceptions {

    USERNAME_NOT_FOUND(HttpStatus.UNAUTHORIZED,"USER-401","일치하는 유저를 찾는데 실패하였습니다."),
    USER_EMAIL_NOT_FOUND(HttpStatus.UNAUTHORIZED,"USER_EMAIL-401","일치하는 유저의 이메일을 찾는데 실패하였습니다.")
    ;

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
