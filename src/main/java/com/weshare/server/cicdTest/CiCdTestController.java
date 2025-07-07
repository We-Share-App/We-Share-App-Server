package com.weshare.server.cicdTest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ci-cd-test")
public class CiCdTestController {
    @GetMapping
    public String test(){
        return "Hello we-share was service";
    }
}
