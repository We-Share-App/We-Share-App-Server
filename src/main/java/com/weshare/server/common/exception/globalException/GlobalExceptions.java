package com.weshare.server.common.exception.globalException;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT) // 필드 값을 JSON으로 변환
public enum GlobalExceptions {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"INTERNAL-500","구체적 원인을 알 수 없는 서버 오류가 발생하였습니다."),
    URL_NOT_FOUND(HttpStatus.NOT_FOUND,"URL-404","존재하지 않는 경로입니다."),
    NOT_ALLOWED_METHOD(HttpStatus.METHOD_NOT_ALLOWED,"METHOD-405","잘못된 메서드 요청입니다."),
    NOT_SUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE,"MEDIA-415","요청 메시지가 잘못되었습니다.");

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}