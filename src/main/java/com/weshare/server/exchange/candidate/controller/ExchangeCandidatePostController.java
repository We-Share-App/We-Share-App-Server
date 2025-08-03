package com.weshare.server.exchange.candidate.controller;

import com.weshare.server.exchange.candidate.dto.ExchangeCandidateRequest;
import com.weshare.server.exchange.candidate.dto.ExchangeCandidateResponse;
import com.weshare.server.exchange.candidate.service.ExchangeCandidatePostAggregateService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "물품교환 후보품 등록 API",
            description = "post로 게시글 정보를 images로 이미지 데이터를 받아 물품교환 후보로 등록하는 API로, 해당 API를 통해 등록된 물품을 물품교환 제안시 사용 가능함"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExchangeCandidateResponse> createExchangeCandidatePost(@RequestPart("post") @Valid ExchangeCandidateRequest request, @RequestPart("images") List<MultipartFile> images, @AuthenticationPrincipal CustomOAuth2User principal){
        ExchangeCandidateResponse response = exchangeCandidatePostAggregateService.createPostWithImage(request,images,principal);
        return ResponseEntity.ok(response);
    }
}
