package com.weshare.server.user.certification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplicationRunListener;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "이메일 인증 DTO")
public class EmailVerificationRequest {
    @Schema(description = "이메일 주소", example = "user@example.com")
    private String email;
    @Schema(description = "해당 이메일로 전송된 인증 번호", example = "ABCDEF")
    private String key;
}
