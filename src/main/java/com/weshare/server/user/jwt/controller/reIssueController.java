package com.weshare.server.user.jwt.controller;

import com.weshare.server.user.jwt.dto.ReIssueResponse;
import com.weshare.server.user.jwt.service.ReIssueService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Refresh 토큰이 타당할 경우 ACCESS/REFRESH 토큰 재발급",
            description = "헤더 access 필드를 통해 ACCESS 토큰을 재발급, 쿠키 refresh 필드를 통해 Refresh 토큰을 재발급"
    )
    @PostMapping()
    public ResponseEntity<ReIssueResponse> reissue(HttpServletRequest request, HttpServletResponse response){
        return reIssueService.createNewTokens(request,response);
    }
}
