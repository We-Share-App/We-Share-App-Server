package com.weshare.server.user.oauthJwt.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//JWTFilter 내에서 예외(JWTException)가 발생하면 처리하는 메서드 (시큐리티에서의 일종의 예외 핸들러)
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // JWTException 이 정상적으로 발생한 경우
        if(authException instanceof JWTException jwtException){
            HttpStatus status = jwtException.getHttpStatus();
            JWTErrorResponse body = new JWTErrorResponse(jwtException);

            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(mapper.writeValueAsString(body));
        }
        // JWTException 이 아닌 예기치 못한 예외가 발생한 경우
        else{
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("상세 정보 알 수 없음");
        }
    }
}
