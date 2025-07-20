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
    USER_EMAIL_NOT_FOUND(HttpStatus.UNAUTHORIZED,"USER_EMAIL-401","일치하는 유저의 이메일을 찾는데 실패하였습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST,"USER_NOT_FOUND_400","해당 사용자를 찾을수 없습니다,"),
    NOT_AVAILABLE_NICKNAME(HttpStatus.BAD_REQUEST,"NOT_AVAILABLE_NICKNAME","사용할 수 없는 닉네임 입니다."),
    ;

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
