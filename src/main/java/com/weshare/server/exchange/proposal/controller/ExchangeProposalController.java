package com.weshare.server.exchange.proposal.controller;

import com.weshare.server.exchange.candidate.dto.ExchangeCandidatePostDto;
import com.weshare.server.exchange.proposal.dto.CandidateResponse;
import com.weshare.server.exchange.proposal.service.ExchangeProposalAggregateService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges/proposal")
public class ExchangeProposalController {
    private final ExchangeProposalAggregateService exchangeProposalAggregateService;

    @GetMapping("/candidates")
    public ResponseEntity<CandidateResponse> getCandidateList(@RequestParam("exchangeId") Long exchangeId, @AuthenticationPrincipal CustomOAuth2User principal){
        // 공개 물품 교환 게시글 작성자가 원하는 카테고리 불러오기
        List<String> categoryList = exchangeProposalAggregateService.getExchangePostWishCategoryList(exchangeId);
        // 조회자가 작성해둔 물품교환 후보 게시글 불러오기
        List<ExchangeCandidatePostDto> exchangeCandidatePostDtoList = exchangeProposalAggregateService.getAllExchangeCandidatePostDtoList(exchangeId);
        CandidateResponse response = CandidateResponse.builder()
                .wishCategoryNameList(categoryList)
                .candidateCount(exchangeCandidatePostDtoList.size())
                .exchangeCandidatePostDtoList(exchangeCandidatePostDtoList)
                .build();

        return ResponseEntity.ok(response);
    }
}
