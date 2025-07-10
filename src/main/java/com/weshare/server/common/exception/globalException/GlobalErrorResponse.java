package com.weshare.server.common.exception.globalException;

import com.weshare.server.common.exception.base.BaseErrorResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@Getter
public class GlobalErrorResponse extends BaseErrorResponse {
    private String stackTrace;   // dev 환경일때 사용되는 디버깅용 stackTrace

    public GlobalErrorResponse(GlobalException exception){
        super(exception);
    }

    public void setStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        this.stackTrace = sw.toString();
    }
}