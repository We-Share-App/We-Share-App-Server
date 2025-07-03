package com.weshare.server.common.exception.globalException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@RestControllerAdvice
@Order(2)
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final String devMode = "dev";
    private final Environment env;

    // 각 도메인에서 처리되지 못한 모든 GlobalException 핸들러
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<GlobalErrorResponse> globalErrorHandler(GlobalException e){
        log.error("[UNHANDLED_GlobalException] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException exception = new GlobalException(GlobalExceptions.INTERNAL_SERVER_ERROR);
        GlobalErrorResponse globalErrorResponse = new GlobalErrorResponse(exception);

        // application.yml 의 active profile 이 "dev" 일때만 stackTrace 를 응답으로 줌
        if(Arrays.asList(env.getActiveProfiles()).contains(devMode)){
            globalErrorResponse.setStackTrace(e);
        }
        return ResponseEntity.status(exception.getHttpStatus()).body(globalErrorResponse);
    }

    // globalException 이외의 모든 예외 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalErrorResponse> internalServerErrorHandler(Exception e){
        log.error("[UNHANDLED_EXCEPTIONS] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException exception = new GlobalException(GlobalExceptions.INTERNAL_SERVER_ERROR);
        GlobalErrorResponse globalErrorResponse = new GlobalErrorResponse(exception);

        // application.yml 의 active profile 이 "dev" 일때만 stackTrace 를 응답으로 줌
        if(Arrays.asList(env.getActiveProfiles()).contains(devMode)){
            globalErrorResponse.setStackTrace(e);
        }
        return ResponseEntity.status(exception.getHttpStatus()).body(globalErrorResponse);
    }

    // 404 글로벌 예외처리
    @ExceptionHandler (NoHandlerFoundException.class)
    public ResponseEntity<GlobalErrorResponse> notFoundErrorHandler(NoHandlerFoundException e){
        log.error("[NoHandlerFoundException] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException exception = new GlobalException(GlobalExceptions.URL_NOT_FOUND);
        GlobalErrorResponse globalErrorResponse = new GlobalErrorResponse(exception);

        // application.yml 의 active profile 이 "dev" 일때만 stackTrace 를 응답으로 줌
        if(Arrays.asList(env.getActiveProfiles()).contains(devMode)){
            globalErrorResponse.setStackTrace(e);
        }
        return ResponseEntity.status(exception.getHttpStatus()).body(globalErrorResponse);
    }

    //405 글로벌 예외처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<GlobalErrorResponse> notAllowedMethodHandler(HttpRequestMethodNotSupportedException e) {
        log.error("[HttpRequestMethodNotSupportedException] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException exception = new GlobalException(GlobalExceptions.NOT_ALLOWED_METHOD);
        GlobalErrorResponse globalErrorResponse = new GlobalErrorResponse(exception);

        // application.yml 의 active profile 이 "dev" 일때만 stackTrace 를 응답으로 줌
        if(Arrays.asList(env.getActiveProfiles()).contains(devMode)){
            globalErrorResponse.setStackTrace(e);
        }
        return ResponseEntity.status(exception.getHttpStatus()).body(globalErrorResponse);
    }

    // 415 글로벌 예외처리
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<GlobalErrorResponse> notSupportedExceptionHandler(HttpMediaTypeNotSupportedException e){
        log.error("[HttpMediaTypeNotSupportedException] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException exception = new GlobalException(GlobalExceptions.NOT_SUPPORTED_MEDIA_TYPE);
        GlobalErrorResponse globalErrorResponse = new GlobalErrorResponse(exception);

        // application.yml 의 active profile 이 "dev" 일때만 stackTrace 를 응답으로 줌
        if(Arrays.asList(env.getActiveProfiles()).contains(devMode)){
            globalErrorResponse.setStackTrace(e);
        }
        return ResponseEntity.status(exception.getHttpStatus()).body(globalErrorResponse);
    }
}