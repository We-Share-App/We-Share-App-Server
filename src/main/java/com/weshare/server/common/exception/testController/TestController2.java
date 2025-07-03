package com.weshare.server.common.exception.testController;

import com.weshare.server.common.exception.globalException.GlobalException;
import com.weshare.server.common.exception.globalException.GlobalExceptions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestController
@RequestMapping("/exception/test")
public class TestController2 {

    @GetMapping("/globalException")
    public void globalExceptionTest(){
        throw new GlobalException(GlobalExceptions.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/runtimeException")
    public void runtimeExceptionTest(){
        throw new RuntimeException();
    }

    @GetMapping("/exception")
    public void exceptionTest()throws Exception {
        throw new Exception("Exception 테스트");
    }

    @PostMapping("/HttpMessageNotReadableException")
    public void notReadableExceptionTest(HttpServletRequest request) throws HttpMessageNotReadableException{
        HttpInputMessage inputMessage = new ServletServerHttpRequest(request);
        throw new HttpMessageNotReadableException("HttpMessageNotReadableException 테스트", inputMessage);
    }

    @PostMapping("/MissingServletRequestParameterException")
    public String requestParameterExceptionTest() throws MissingServletRequestParameterException {
        throw new MissingServletRequestParameterException("requiredParam", "String");
    }

    @GetMapping("/MethodArgumentTypeMismatchException")
    public String argumentTypeMismatchExceptionTest() throws MethodArgumentTypeMismatchException {
        throw new MethodArgumentTypeMismatchException("abc", Long.class, "id", null, null);
    }

    @GetMapping("/NoHandlerFoundException")
    public void noHandlerFoundExceptionTest(HttpServletRequest request) throws NoHandlerFoundException{
        HttpHeaders httpHeaders = new HttpHeaders();
        throw new NoHandlerFoundException(request.getMethod(), request.getRequestURI(),httpHeaders);
    }

    @GetMapping("/HttpRequestMethodNotSupportedException")
    public void HttpRequestMethodNotSupportedExceptionTest(HttpServletRequest request)throws HttpRequestMethodNotSupportedException{
        throw new HttpRequestMethodNotSupportedException(request.getMethod());
    }

    @GetMapping("/HttpMediaTypeNotSupportedException")
    public void httpMediaTypeNotSupportedExceptionTest(HttpServletRequest request) throws HttpMediaTypeNotSupportedException {
        String contentType = request.getContentType();
        throw new HttpMediaTypeNotSupportedException(contentType);
    }
}
