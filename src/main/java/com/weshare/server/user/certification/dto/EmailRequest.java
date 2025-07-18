package com.weshare.server.user.certification.dto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "이메일 인증번호 전송 요청 DTO")
public class EmailRequest {
    @Schema(description = "이메일 주소", example = "user@example.com")
    private String email;
}
