package com.weshare.server.aws.s3.exception;

import com.weshare.server.common.exception.base.BaseException;

public class S3Exception extends BaseException {
    public S3Exception(S3Exceptions exceptions){
        super(exceptions.getErrorType(), exceptions.getErrorCode(),exceptions.getMessage());
    }
}
