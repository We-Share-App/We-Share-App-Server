package com.weshare.server.user.jwt.util;

import com.weshare.server.user.entity.UserRole;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
// Access/Refresh 토큰 유틸리티 클래스
public class JWTUtil {
    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // 토큰 -> Username 추출 메서드
    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    // 토큰 -> UserRole 추출 메서드
    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // 토큰 -> 토큰종류(Access/Refresh) 추출 메서드
    public String getCategory(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public boolean isExpired(String token) {
        try {
            Date exp = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration();
            return exp.before(new Date());
        } catch (io.jsonwebtoken.ExpiredJwtException ex) {
            // 만료 예외도 “만료된 토큰”으로 간주
            return true;
        }
    }

    public boolean isValidForm(String token) {
        // 토큰이 아예 없거나 빈 문자열이면 false
        if (!StringUtils.hasText(token)) {
            return false;
        }

        try {
            // 구조·서명·Base64 디코딩까지 모두 시도
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            // 파싱 성공: 완전 유효한 토큰
            return true;
        } catch (ExpiredJwtException ex) {
            // 해당 메서드에서는 만료된 토큰도 형식상 문제는 없기에 정상 토큰으로 판정
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // 서명 불일치, MalformedJwtException 등 포맷 오류
            return false;
        }
    }


    public String createJwt(String category, String username, UserRole userRole, Long expiredMs) {

        return Jwts.builder()
                .claim("category",category)
                .claim("username", username)
                .claim("role", userRole.getRoleName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}