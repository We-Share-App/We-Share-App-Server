package com.weshare.server.common.exception.testController;

import com.weshare.server.common.exception.globalException.GlobalException;
import com.weshare.server.common.exception.globalException.GlobalExceptions;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;



@RestController
@RequestMapping("/exception/test")
public class TestController {

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

    @GetMapping("/MethodArgumentNotValidException")
    public void argumentNotValidExceptionTest()
            throws NoSuchMethodException, MethodArgumentNotValidException {
        // 1) dummy(Object) 메서드의 첫 번째 파라미터 정보를 MethodParameter로 생성
        MethodParameter param = new MethodParameter(
                this.getClass().getDeclaredMethod("dummy", Object.class),
                0
        );
        // 2) 최소한의 BindingResult 생성 (target/newObject, objectName 아무값)
        BindingResult br = new BeanPropertyBindingResult(new Object(), "object");
        // 3) 이 한 줄로 예외 던지기
        throw new MethodArgumentNotValidException(param, br);
    }

    // MethodParameter 참조용 더미 메서드 (실제로 호출되진 않음)
    private void dummy(Object o) { }

    @GetMapping("/AuthenticationException")
    public void authenticationExceptionTest(){
        throw new BadCredentialsException("인증 예외");
    }

    @GetMapping("/AccessDeniedException")
    public void AccessDeniedExceptionTest() throws AccessDeniedException {
        throw new AccessDeniedException("인가 예외");
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
