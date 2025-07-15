package com.weshare.server.user.jwt.filter;

import com.weshare.server.user.jwt.util.JWTUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class JWTFilterConfig {
    // JWTFilter 에 JWTUtil 인젝션
    @Bean
    public JWTFilter jwtFilter(JWTUtil jwtUtil) {
        return new JWTFilter(jwtUtil);
    }
}
