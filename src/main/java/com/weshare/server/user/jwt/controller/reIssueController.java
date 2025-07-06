package com.weshare.server.user.jwt.controller;

import com.weshare.server.user.jwt.dto.ReIssueResponse;
import com.weshare.server.user.jwt.service.ReIssueService;
import com.weshare.server.user.jwt.util.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reissue")
public class reIssueController {

    private final ReIssueService reIssueService;

    @PostMapping()
    public ResponseEntity<ReIssueResponse> reissue(HttpServletRequest request, HttpServletResponse response){
        return reIssueService.createNewTokens(request,response);
    }
}
