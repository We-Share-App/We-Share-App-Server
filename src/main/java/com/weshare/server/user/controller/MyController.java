package com.weshare.server.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Operation(
            summary = "ACCESS 토큰 테스트 API",
            description = "ACCESS 토큰이 정상적인 경우에만 정상 작동"
    )
    @GetMapping("/my")
    public String myAPI(){
        return "my route";
    }
}