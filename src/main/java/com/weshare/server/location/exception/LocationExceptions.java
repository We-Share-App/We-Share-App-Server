package com.weshare.server.location.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum LocationExceptions {

    NOT_EXIST_LOCATION_ID(HttpStatus.BAD_REQUEST,"NOT_EXIST_LOCATION_ID","시스템에 등록되지 않는 위치 ID 입니다.");
    ;

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
