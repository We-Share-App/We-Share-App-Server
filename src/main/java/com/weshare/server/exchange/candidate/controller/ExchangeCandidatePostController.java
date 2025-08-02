package com.weshare.server.exchange.candidate.controller;

import com.weshare.server.exchange.candidate.dto.ExchangeCandidateRequest;
import com.weshare.server.exchange.candidate.dto.ExchangeCandidateResponse;
import com.weshare.server.exchange.candidate.service.ExchangeCandidatePostAggregateService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges/candidates/posts")
public class ExchangeCandidatePostController {
    private final ExchangeCandidatePostAggregateService exchangeCandidatePostAggregateService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExchangeCandidateResponse> createExchangeCandidatePost(@RequestPart("post") @Valid ExchangeCandidateRequest request, @RequestPart("images") List<MultipartFile> images, @AuthenticationPrincipal CustomOAuth2User principal){
        ExchangeCandidateResponse response = exchangeCandidatePostAggregateService.createPostWithImage(request,images,principal);
        return ResponseEntity.ok(response);
    }
}
