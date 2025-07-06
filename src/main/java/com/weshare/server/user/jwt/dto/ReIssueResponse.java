package com.weshare.server.user.jwt.dto;

import com.weshare.server.common.dto.BaseResponse;
import org.springframework.http.HttpStatus;

public class ReIssueResponse extends BaseResponse {
    public ReIssueResponse(HttpStatus httpStatus, String code, String message){
        super(httpStatus,code,message);
    }
}
