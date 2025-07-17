package com.weshare.server.user.certification.dto;

import com.weshare.server.common.dto.BaseResponse;
import org.springframework.http.HttpStatus;

public class CertificationResponse extends BaseResponse {
    public CertificationResponse(HttpStatus httpStatus, String code, String message) {
        super(httpStatus, code, message);
    }
}
