package com.weshare.server.swagger;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

//    @Bean
//    public OpenAPI customOpenApiAccessToken() {
//        String securitySchemeName = "accessToken"; // Security Scheme 이름
//
//        return new OpenAPI()
//                .info(new Info()
//                        .title("We_Gift API") // API 제목
//                        .description("We_Gift 개발용 Swagger") // API 설명
//                        .version("1.0.0")) // 버전 정보
//                .components(new Components()
//                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
//                                .name("access") // 헤더명: access
//                                .type(SecurityScheme.Type.APIKEY) // API Key 방식
//                                .in(SecurityScheme.In.HEADER) // 헤더에 추가
//                        )
//                )
//                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName)); // 모든 API에 보안 요구 사항 추가
//    }

    @Bean
    public OpenAPI customOpenApiAccessToken() {
        String accessScheme = "accessToken";
        String refreshScheme = "refreshToken";

        return new OpenAPI()
                .info(new Info()
                        .title("We_Share API")
                        .description("We_Share 개발용 Swagger")
                        .version("1.0.0"))
                .components(new Components()
                        // Header 기반 accessToken
                        .addSecuritySchemes(accessScheme, new SecurityScheme()
                                .name("access")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                        )
                        // Cookie 기반 refreshToken
                        .addSecuritySchemes(refreshScheme, new SecurityScheme()
                                .name("refresh")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                        )
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList(accessScheme)
                        .addList(refreshScheme)
                );
    }

    @Bean
    public OpenApiCustomizer customOpenApiLogoutEndpoint() {
        return openApi -> {
            Paths paths = openApi.getPaths();
            paths.addPathItem("/logout", new PathItem()
                    .post(new Operation()
                            .summary("유저 로그아웃") // API 요약
                            .description("로그아웃을 수행합니다. Refresh Token을 삭제합니다. " +
                                    "Refresh Token은 브라우저 쿠키에서 자동으로 전송됩니다.") // API 설명 수정
                            .tags(List.of("logout-endpoint")) // 태그 추가
                            .responses(new ApiResponses()
                                    .addApiResponse("200", new ApiResponse()
                                            .description("로그아웃 성공"))
                                    .addApiResponse("400", new ApiResponse()
                                            .description("잘못된 요청 (토큰 없음 또는 만료됨)"))
                                    .addApiResponse("401", new ApiResponse()
                                            .description("인증되지 않음"))
                            )
                    )
            );
        };
    }
}
