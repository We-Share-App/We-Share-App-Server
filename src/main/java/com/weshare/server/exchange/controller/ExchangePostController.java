package com.weshare.server.exchange.controller;


import com.weshare.server.exchange.dto.ExchangePostRequest;
import com.weshare.server.exchange.dto.ExchangePostResponse;
import com.weshare.server.exchange.service.ExchangePostAggregateService;
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
@RequestMapping("/exchanges/posts")
public class ExchangePostController {
    private final ExchangePostAggregateService exchangePostAggregateService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExchangePostResponse> createExchangePost(@RequestPart("post") @Valid ExchangePostRequest request, @RequestPart("images")List<MultipartFile> images, @AuthenticationPrincipal CustomOAuth2User principal) {
        ExchangePostResponse response = exchangePostAggregateService.createPostWithImagesAndCategories(request, images, principal);
        return ResponseEntity.ok(response);

    }
}
