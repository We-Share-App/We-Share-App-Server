package com.weshare.server.user.certification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplicationRunListener;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationRequest {
    private String email;
    private String key;
}
