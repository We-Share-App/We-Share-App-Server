package com.weshare.server.cicdTest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ci-cd-test")
public class CiCdTestController {
    @GetMapping
    public String test(){
        return "CI/CD 최종 테스트 성공 \n 소웨공 We-share\n Let's go !!";
    }
}
