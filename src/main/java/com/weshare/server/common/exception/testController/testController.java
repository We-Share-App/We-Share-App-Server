package com.weshare.server.common.exception.testController;

import com.weshare.server.common.exception.globalException.GlobalException;
import com.weshare.server.common.exception.globalException.GlobalExceptions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;


@RestController
@RequestMapping("/exception/test")
public class testController {

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

    @GetMapping("/noHandlerFoundException")
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
