package com.weshare.server.common.exception.globalException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;

@RestControllerAdvice
@Order(2)
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private static final String devMode = "dev";
    private final Environment env;

    // 각 도메인에서 처리되지 못한 모든 GlobalException 핸들러
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<GlobalErrorResponse> globalErrorHandler(GlobalException e){
        log.error("[UNHANDLED_GlobalException] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException globalException = new GlobalException(GlobalExceptions.INTERNAL_SERVER_ERROR);
        return resolveExceptionResponse(e,globalException);
    }

    // globalException 이외의 모든 예외 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalErrorResponse> internalServerErrorHandler(Exception e){
        log.error("[UNHANDLED_EXCEPTIONS] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException globalException = new GlobalException(GlobalExceptions.INTERNAL_SERVER_ERROR);
        return resolveExceptionResponse(e,globalException);
    }

    // 400 글로벌 처리 - 역직렬화 실패
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalErrorResponse> notReadableHandler(HttpMessageNotReadableException e){
        log.error("[HttpMessageNotReadableException] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException globalException = new GlobalException(GlobalExceptions.NOT_READABLE);
        return resolveExceptionResponse(e,globalException);
    }

    // 400 글로벌 처리 - 파라미터 누락
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GlobalErrorResponse> missingParamHandler(MissingServletRequestParameterException e) {
        log.error("[MissingServletRequestParameterException] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException globalException = new GlobalException(GlobalExceptions.MISSED_PARAMETER);
        return resolveExceptionResponse(e,globalException);
    }

    // 400 글로벌 처리 - URL 타입 에러
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GlobalErrorResponse> typeMismatchHandler(MethodArgumentTypeMismatchException e) {
        log.error("[MethodArgumentTypeMismatchException] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException globalException = new GlobalException(GlobalExceptions.ARGUMENT_TYPE_MISMATCH);
        return resolveExceptionResponse(e,globalException);
    }


    // TODO MethodArgumentNotValidException - 400 처리

    // TODO AuthenticationException - 401 처리

    // TODO AccessDeniedException - 403 처리

    // 404 글로벌 예외처리
    @ExceptionHandler (NoHandlerFoundException.class)
    public ResponseEntity<GlobalErrorResponse> notFoundErrorHandler(NoHandlerFoundException e){
        log.error("[NoHandlerFoundException] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException globalException = new GlobalException(GlobalExceptions.URL_NOT_FOUND);
        return  resolveExceptionResponse(e,globalException);
    }

    //405 글로벌 예외처리
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<GlobalErrorResponse> notAllowedMethodHandler(HttpRequestMethodNotSupportedException e) {
        log.error("[HttpRequestMethodNotSupportedException] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException globalException = new GlobalException(GlobalExceptions.NOT_ALLOWED_METHOD);
        return resolveExceptionResponse(e,globalException);
    }

    // 415 글로벌 예외처리
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<GlobalErrorResponse> notSupportedExceptionHandler(HttpMediaTypeNotSupportedException e){
        log.error("[HttpMediaTypeNotSupportedException] {}: {} \n Exception stackTrace: ", e.getClass().getName(), e.getMessage(),e);
        GlobalException globalException = new GlobalException(GlobalExceptions.NOT_SUPPORTED_MEDIA_TYPE);
        return resolveExceptionResponse(e, globalException);
    }

    private ResponseEntity<GlobalErrorResponse> resolveExceptionResponse(Exception e,GlobalException exception){
        GlobalErrorResponse globalErrorResponse = new GlobalErrorResponse(exception);
        // application.yml 의 active profile 이 "dev" 일때만 stackTrace 를 응답으로 줌
        if(Arrays.asList(env.getActiveProfiles()).contains(devMode)){
            globalErrorResponse.setStackTrace(e);
        }
        return ResponseEntity.status(exception.getHttpStatus()).body(globalErrorResponse);
    }
}