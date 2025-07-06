package com.weshare.server.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public abstract class BaseResponse {
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
