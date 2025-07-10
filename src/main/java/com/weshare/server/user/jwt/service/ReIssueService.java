package com.weshare.server.user.jwt.service;

import com.weshare.server.user.jwt.dto.ReIssueResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface ReIssueService {
    public ResponseEntity<ReIssueResponse> createNewTokens(HttpServletRequest request, HttpServletResponse response);
}
