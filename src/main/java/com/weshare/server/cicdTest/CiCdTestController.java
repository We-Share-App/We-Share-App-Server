package com.weshare.server.cicdTest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ci-cd-test")
public class CiCdTestController {
    @GetMapping
    public String test(){
        return "[CI/CD 최종 테스트 성공] - 팀 We-share Let's go !!";
    }
}
