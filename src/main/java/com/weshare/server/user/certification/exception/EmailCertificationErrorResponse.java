package com.weshare.server.user.certification.exception;

import com.weshare.server.common.dto.BaseResponse;
import lombok.Getter;

@Getter
public class EmailCertificationErrorResponse extends BaseResponse {
    public EmailCertificationErrorResponse(EmailCertificationException exception){
        super(exception.getHttpStatus(), exception.getErrorCode(), exception.getMessage());
    }
}
