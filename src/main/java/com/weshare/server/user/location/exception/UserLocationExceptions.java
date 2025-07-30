package com.weshare.server.user.location.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserLocationExceptions {
    LOCATION_ALREADY_REGISTERED(HttpStatus.BAD_REQUEST,"ALREADY_REGISTERED_USER_LOCATION","이미 해당 사용자가 등록한 위치 입니다."),
    LOCATION_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST,"USER_LOCATION_LIMIT_EXCEEDED","해당 사용자는 이미 2개의 위치 정보를 저장하였습니다. 기존 정보를 변경하거나 삭제해야합니다."),
    USER_LOCATION_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"USER_LOCATION_CREATION_FAILED","UserLocation 생성에 실패하였습니다.");
    ;
    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
