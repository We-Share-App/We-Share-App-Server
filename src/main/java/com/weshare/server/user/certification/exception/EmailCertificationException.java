package com.weshare.server.user.certification.exception;

import com.weshare.server.common.exception.base.BaseException;
import lombok.Getter;

@Getter
public class EmailCertificationException extends BaseException {
    public EmailCertificationException(EmailCertificationExceptions exceptions){
        super(exceptions.getErrorType(),exceptions.getErrorCode(),exceptions.getMessage());
    }
}
