package com.weshare.server.user.controller;

import com.weshare.server.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginUrlResponseController {

    @GetMapping("/login")
    public ResponseEntity<BaseLoginResponse> loginPage(){
        BaseLoginResponse response = new BaseLoginResponse(HttpStatus.OK,"LOGIN-200","OAuth2 기반 로그인으로 login 페이지를 제공하지 않습니다.");
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @Getter
    public static class BaseLoginResponse extends BaseResponse {
        public BaseLoginResponse(HttpStatus httpStatus, String code, String message) {
            super(httpStatus, code, message);
        }
    }
}
