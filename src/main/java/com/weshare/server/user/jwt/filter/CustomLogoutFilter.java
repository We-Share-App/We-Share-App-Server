package com.weshare.server.user.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weshare.server.common.dto.BaseResponse;
import com.weshare.server.user.jwt.util.JWTUtil;
import com.weshare.server.user.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    // "/logout" 으로 post 요청을 보낼경우
    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String requestUri = request.getRequestURI();
        if (!requestUri.matches("^\\/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("[CustomLogoutFilter] 로그아웃 요청");
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            sendJson(response, new LogoutResponse(HttpStatus.METHOD_NOT_ALLOWED, "LOGOUT-405", "POST 메서드만 허용됩니다."));
            return;
        }

        //get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {

            if (cookie.getName().equals("refresh")) {

                refresh = cookie.getValue();
            }
        }

        //refresh null check
        if (refresh == null) {
            log.info("[LOGOUT] Refresh Token Is NULL");
            sendJson(response, new LogoutResponse(HttpStatus.BAD_REQUEST, "LOGOUT-400", "쿠키에서 refresh token을 찾을 수 없습니다."));
            return;
        }

        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {

            log.info("[LOGOUT] Refresh Token Is Expired");
            sendJson(response, new LogoutResponse(HttpStatus.UNAUTHORIZED, "LOGOUT-401", "이미 만료된 refresh token 입니다."));
            return;
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {

            log.info("[LOGOUT] Not A Refresh Token");
            //response status code
            sendJson(response, new LogoutResponse(HttpStatus.BAD_REQUEST, "LOGOUT-402", "refresh token이 아닙니다."));
            return;
        }

        //DB에 저장되어 있는지 확인
        Boolean isExist = refreshRepository.existsByRefresh(refresh);
        if (!isExist) {
            log.info("[LOGOUT] Not Registered Refresh Token At DB");
            //response status code
            sendJson(response, new LogoutResponse(HttpStatus.BAD_REQUEST, "LOGOUT-403", "DB에 등록되지 않은 토큰입니다."));
            return;
        }


        //로그아웃 진행
        //Refresh 토큰 DB에서 제거
        refreshRepository.deleteByRefresh(refresh);

        //Refresh 토큰 Cookie 값 0
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        response.addCookie(cookie);
        sendJson(response, new LogoutResponse(HttpStatus.OK, "LOGOUT-200", "로그아웃 처리되었습니다."));
    }

    private void sendJson(HttpServletResponse response, BaseResponse body) throws IOException {
        response.setStatus(body.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), body);
    }

    public static class LogoutResponse extends BaseResponse {
        public LogoutResponse(HttpStatus httpStatus, String code, String message) {
            super(httpStatus, code, message);
        }
    }
}