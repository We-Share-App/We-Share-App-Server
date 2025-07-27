package com.weshare.server.exchange.proposal.controller;

import com.weshare.server.exchange.proposal.dto.ExchangeProposalRequest;
import com.weshare.server.exchange.proposal.dto.ExchangeProposalResponse;
import com.weshare.server.exchange.proposal.service.ExchangeProposalPostAggregateService;
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
@RequestMapping("/exchanges/proposals/posts")
public class ExchangeProposalPostController {
    private final ExchangeProposalPostAggregateService exchangeProposalPostAggregateService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExchangeProposalResponse> createExchangeProposalPost(@RequestPart("post") @Valid ExchangeProposalRequest request, @RequestPart("images") List<MultipartFile> images, @AuthenticationPrincipal CustomOAuth2User principal){
        ExchangeProposalResponse response = exchangeProposalPostAggregateService.createPostWithImage(request,images,principal);
        return ResponseEntity.ok(response);
    }
}
