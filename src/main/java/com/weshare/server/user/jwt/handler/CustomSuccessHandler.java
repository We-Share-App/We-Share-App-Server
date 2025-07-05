package com.weshare.server.user.jwt.handler;

import com.weshare.server.user.entity.Refresh;
import com.weshare.server.user.entity.UserRole;
import com.weshare.server.user.jwt.util.JWTUtil;
import com.weshare.server.user.jwt.oauthJwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.repository.RefreshRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;


    // 로그인 성공시 JWT 발급 메서드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String accessToken = jwtUtil.createJwt("access",username, UserRole.stringToUserRole(role) , 600000L); // Access 토큰
        String refreshToken = jwtUtil.createJwt("refresh",username, UserRole.stringToUserRole(role),86400000L); // refresh 토큰

        addRefreshEntity(username,refreshToken,86400000L); // DB refresh 토큰 정보 저장

        response.setHeader("access",accessToken); // 헤더의 access 필드를 통해 access 토큰 전달

        response.addCookie(createCookie("refresh",refreshToken)); // 쿠키의 refresh 필드를 통해 refresh 토큰 전달
        response.setStatus(HttpServletResponse.SC_OK);

    }

    // 쿠키 추가 메서드
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    private void addRefreshEntity(String username, String refreshToken, Long expiredMs){
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Refresh refreshEntity = Refresh.builder()
                .username(username)
                .refresh(refreshToken)
                .expiration(date.toString())
                .build();

        refreshRepository.save(refreshEntity);
    }
}