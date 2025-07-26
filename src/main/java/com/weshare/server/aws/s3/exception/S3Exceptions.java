package com.weshare.server.aws.s3.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum S3Exceptions {

    IMAGE_EXTENSION_NOT_SUPPORTED(HttpStatus.BAD_REQUEST,"IMAGE-400","지원되지 않는 이미지 형식"),
    IMAGE_UPLOAD_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR,"IMAGE-500","이미지 업로드 실패"),
    IMAGE_SERIALIZE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"IMAGEPROCESS-500","이미지 직렬화 실패");

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
