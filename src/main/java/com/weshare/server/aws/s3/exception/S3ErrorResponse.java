package com.weshare.server.aws.s3.exception;

import com.weshare.server.common.exception.base.BaseErrorResponse;

public class S3ErrorResponse extends BaseErrorResponse {
    public S3ErrorResponse(S3Exception exception){
        super(exception.getHttpStatus(),exception.getErrorCode(),exception.getMessage());
    }
}
