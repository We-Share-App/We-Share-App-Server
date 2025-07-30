package com.weshare.server.user.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weshare.server.user.entity.Refresh;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.entity.UserRole;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.exception.JWTException;
import com.weshare.server.user.jwt.exception.JWTExceptions;
import com.weshare.server.user.jwt.util.JWTUtil;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.repository.RefreshRepository;
import com.weshare.server.user.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Transactional
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final UserRepository userRepository;


    // 로그인 성공시 JWT 발급 메서드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        // 이메일 인증을 받지 않은 사용자는 토큰을 발급해주지 않음
        User user = userRepository.findByUsername(username).orElseThrow(()->new UserException(UserExceptions.USER_NOT_FOUND));
        if(!user.getIsCertificated()){
            //throw new JWTException(JWTExceptions.NOT_VERIFIED_EMAIL_USER);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new ObjectMapper()
                    .writeValueAsString(Map.of(
                            "httpStatus", "UNAUTHORIZED",
                            "code", "EMAIL-UNVERIFIED",
                            "message", "이메일 인증이 필요합니다."
                    ))
            );
            return; // 더 이상 진행하지 않고 종료
        }

        // 이메일 인증을 진행한 기존 사용자는 토큰을 정상적으로 발급해줌
        Boolean isFirstLogin = false;
        if(user.getNickname() == null){
            isFirstLogin = true;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String accessToken = jwtUtil.createJwt("access",username, UserRole.stringToUserRole(role) , 7200000L); // Access 토큰 (유효시간 2시간)
        String refreshToken = jwtUtil.createJwt("refresh",username, UserRole.stringToUserRole(role),86400000L); // refresh 토큰 (유효시간 24시간)

        addRefreshEntity(username,refreshToken,86400000L); // DB refresh 토큰 정보 저장

        response.setHeader("access",accessToken); // 헤더의 access 필드를 통해 access 토큰 전달
        response.setHeader("is_first_login",isFirstLogin.toString());

        response.addCookie(createCookie("access", accessToken));
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