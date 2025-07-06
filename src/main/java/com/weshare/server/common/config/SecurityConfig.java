package com.weshare.server.common.config;

import com.weshare.server.user.jwt.filter.CustomLogoutFilter;
import com.weshare.server.user.jwt.handler.CustomSuccessHandler;
import com.weshare.server.user.jwt.filter.JWTFilter;
import com.weshare.server.user.jwt.util.JWTUtil;
import com.weshare.server.user.jwt.exception.JWTAuthenticationEntryPoint;
import com.weshare.server.user.jwt.oauthJwt.service.CustomOAuth2UserService;
import com.weshare.server.user.repository.RefreshRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;
    private final JWTFilter jwtFilter;
    private final RefreshRepository refreshRepository;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());

        //HTTP Basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        // JWTFilter 에서 발생하는 모든 예외는 JWTAuthenticationEntryPoint 에서 처리하도록 설정
        http
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        // Logout 시점에 login redirect 방지
        http
                .logout(logout -> logout.disable());

        // CustomLogoutFilter 등록
        http.addFilterAt(
                new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

        //JWTFilter 등록
        http.addFilterAfter(jwtFilter, ExceptionTranslationFilter.class);

        //oauth2
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                );


        //세션 설정 : STATELESS
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // URL 별 권한 설정
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/v3/**","/swagger-ui/**","/logout").permitAll() // 스웨거 전체 허용 (임시)
                        .anyRequest().authenticated());

        // CORS 설졍
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Set-Cookie"));
                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));
                        configuration.setExposedHeaders(Collections.singletonList("access"));
                        return configuration;
                    }
                }));

        return http.build();
    }
}