package com.weshare.server.user.jwt.service;

import com.weshare.server.user.entity.Refresh;
import com.weshare.server.user.entity.UserRole;
import com.weshare.server.user.jwt.dto.ReIssueResponse;
import com.weshare.server.user.jwt.util.JWTUtil;
import com.weshare.server.user.repository.RefreshRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReIssueServiceImpl implements ReIssueService{

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;


    @Override
    @Transactional
    public ResponseEntity<ReIssueResponse> createNewTokens(HttpServletRequest request, HttpServletResponse response) {

        // refreshToken 추출
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        // cookies가 null인지 먼저 검사
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    refresh = cookie.getValue();
                    break;
                }
            }
        }

        // 리프레시 토큰을 찾을수 없는 경우
        if (refresh == null) {
            ReIssueResponse reIssueResponse = new ReIssueResponse(HttpStatus.BAD_REQUEST,"RE_ISSUE-400","refresh token을 찾을수 없습니다.");
            return ResponseEntity.status(reIssueResponse.getHttpStatus()).body(reIssueResponse);
        }

        // 리프레시 토큰이 만료된 경우
        if(jwtUtil.isExpired(refresh)){
            ReIssueResponse reIssueResponse = new ReIssueResponse(HttpStatus.UNAUTHORIZED,"REFRESH-401","이미 만료된 refreshToken 입니다.");
            return ResponseEntity.status(reIssueResponse.getHttpStatus()).body(reIssueResponse);
        }

        // 리프레시 토큰이 아닌 경우
        if(!jwtUtil.getCategory(refresh).equals("refresh")){
            ReIssueResponse reIssueResponse = new ReIssueResponse(HttpStatus.UNAUTHORIZED,"REFRESH-400","refreshToken 이 아닙니다.");
            return ResponseEntity.status(reIssueResponse.getHttpStatus()).body(reIssueResponse);
        }

        // DB에 저장되지 않은 리프레시 토큰인 경우
        if(!refreshRepository.existsByRefresh(refresh)){
            ReIssueResponse reIssueResponse = new ReIssueResponse(HttpStatus.UNAUTHORIZED,"REFRESH-401","유효한 refreshToken 이 아닙니다.");
            return ResponseEntity.status(reIssueResponse.getHttpStatus()).body(reIssueResponse);
        }

        // 새로운 access/refresh 생성
        String username = jwtUtil.getUsername(refresh);
        UserRole userRole = UserRole.stringToUserRole(jwtUtil.getRole(refresh));
        String newAccess = jwtUtil.createJwt("access", username, userRole, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, userRole, 86400000L);

        refreshRepository.deleteByRefresh(refresh);
        addRefreshEntity(username, newRefresh, 86400000L);

        //response
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        ReIssueResponse reIssueResponse = new ReIssueResponse(HttpStatus.OK,"RE_ISSUE-200","access, refresh 토큰 재발급 성공");
        return ResponseEntity.status(reIssueResponse.getHttpStatus()).body(reIssueResponse);
    }

    private void addRefreshEntity(String username, String refreshToken, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Refresh refresh = Refresh.builder()
                .username(username)
                .refresh(refreshToken)
                .expiration(date.toString())
                .build();

        refreshRepository.save(refresh);
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
