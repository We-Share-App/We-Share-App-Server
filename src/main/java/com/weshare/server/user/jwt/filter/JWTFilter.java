package com.weshare.server.user.jwt.filter;

import com.weshare.server.user.jwt.util.JWTUtil;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.jwt.dto.UserDTO;
import com.weshare.server.user.entity.UserRole;
import com.weshare.server.user.jwt.exception.JWTException;
import com.weshare.server.user.jwt.exception.JWTExceptions;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    // 스웨거, 로그아웃, 리프레시 토큰 재발급은 JWTFilter 를 생략하고 다음 필터로 넘어간다.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();
        DispatcherType dtType   = request.getDispatcherType();

        log.debug("[JWTFilter] shouldNotFilter? path={}, method={}", path, method);

        // (A) CORS preflight OPTIONS 요청 또는 스프링 에러 디스패처 요청 제외
        if ("OPTIONS".equalsIgnoreCase(method) || dtType == DispatcherType.ERROR) {
            return true;
        }

        // (B) 정확히 매칭해서 제외할 경로
        if (path.equals("/error")
                || path.equals("/logout")
                || path.equals("/reissue")) {
            return true;
        }

        // (C) 특정 prefix 로 시작하는 요청 제외
        if (path.startsWith("/v3/")
                || path.startsWith("/test")
                || path.startsWith("/swagger-ui/")
                || path.equals("/swagger-ui.html")
                || path.equals("/swagger-ui/index.html")
                || path.startsWith("/user/email/certification")
                || path.startsWith("/oauth2/authorization")
                || path.startsWith("/login/oauth2/code")
                || path.startsWith("/login")
                || path.startsWith("/webjars/")
                || path.startsWith("/.well-known/appspecific")) {
            return true;
        }

        // (D) 정적 리소스 확장자 기반 제외
        if (path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.endsWith(".css")
                || path.endsWith(".js")
                || path.endsWith(".map")
                || path.endsWith(".ico")
                || path.endsWith("inject.js")
                || path.endsWith("contentscript.js")) {
            return true;
        }

        // 그 외 요청만 필터 적용
        return false;
    }

    // 클라이언트의 Access 토큰 검사 메서드
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("access");

        //access 헤더 검증
        // null 이거나, 공백(empty) 이면 NO_ACCESS_TOKEN
        if (!StringUtils.hasText(accessToken)) {
            throw new JWTException(JWTExceptions.NO_ACCESS_TOKEN);
        }

        // 토큰 형식(손상) 여부 검증
        if(!jwtUtil.isValidForm(accessToken)){
            log.info("[JWTFilter] Malformed AccessToken");
            throw new JWTException(JWTExceptions.MALFORMED_ACCESS_TOKEN);
        }

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(accessToken)) {

            log.info("[JWTFilter] Expired AccessToken");
            throw new JWTException(JWTExceptions.EXPIRED_ACCESS_TOKEN);
        }

        // 토큰 종류 판별 (access/refresh)
        String category = jwtUtil.getCategory(accessToken);
        if(!category.equals("access")){
            log.info("[JWTFilter] Not A AccessToken");
            throw new JWTException(JWTExceptions.NOT_A_ACCESS_TOKEN);
        }

        //토큰에서 username과 stringUserRole 획득
        String username = jwtUtil.getUsername(accessToken);
        String stringUserRole = jwtUtil.getRole(accessToken);

        //userDTO를 생성
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setUserRole(UserRole.stringToUserRole(stringUserRole));

        //UserDetails 에 회원 정보 객체 담기
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}