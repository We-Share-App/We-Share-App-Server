package com.weshare.server.user.jwt;

import com.weshare.server.user.dto.CustomOAuth2User;
import com.weshare.server.user.dto.UserDTO;
import com.weshare.server.user.entity.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }

    // 클라이언트의 Access 토큰 검사 메서드
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = request.getHeader("access");

        //access 헤더 검증
        if (accessToken == null) {

            log.info("[JWTFilter] NULL AccessToken");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(accessToken)) {

            log.info("[JWTFilter] Expired AccessToken");
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        // 토큰 종류 판별 (access/refresh)
        String category = jwtUtil.getCategory(accessToken);
        if(!category.equals("access")){
            log.info("[JWTFilter] Not A AccessToken");

            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
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