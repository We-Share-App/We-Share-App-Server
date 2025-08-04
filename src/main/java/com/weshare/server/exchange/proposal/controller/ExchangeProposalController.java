package com.weshare.server.exchange.proposal.controller;

import com.weshare.server.exchange.candidate.dto.ExchangeCandidatePostDto;
import com.weshare.server.exchange.proposal.dto.CandidateResponse;
import com.weshare.server.exchange.proposal.dto.ExchangeProposalRequest;
import com.weshare.server.exchange.proposal.dto.ExchangeProposalResponse;
import com.weshare.server.exchange.proposal.service.ExchangeProposalAggregateService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges/proposal")
public class ExchangeProposalController {
    private final ExchangeProposalAggregateService exchangeProposalAggregateService;

    @Operation(
            summary = "물품교환 등록 가능 후보품 조회 API",
            description = "자신이 등록한 후보품과 해당 공개 물품교환 게시글 작성자가 선호하는 물품 카테고리 정보를 응답하는 API"
    )
    @GetMapping("/candidates")
    public ResponseEntity<CandidateResponse> getCandidateList(@RequestParam("exchangeId") Long exchangeId, @AuthenticationPrincipal CustomOAuth2User principal){
        // 공개 물품 교환 게시글 작성자가 원하는 카테고리 불러오기
        List<String> categoryList = exchangeProposalAggregateService.getExchangePostWishCategoryList(exchangeId);
        // 조회자가 작성해둔 물품교환 후보 게시글 불러오기
        List<ExchangeCandidatePostDto> exchangeCandidatePostDtoList = exchangeProposalAggregateService.getAllExchangeCandidatePostDtoList(exchangeId,principal);
        CandidateResponse response = CandidateResponse.builder()
                .wishCategoryNameList(categoryList)
                .candidateCount(exchangeCandidatePostDtoList.size())
                .exchangeCandidatePostDtoList(exchangeCandidatePostDtoList)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "물품교환 신청 API",
            description = "공개 물품교환 게시글 ID를 기준으로 물품 교환 후보품들과의 교환 요청을 보내는 API"
    )
    @PostMapping()
    public ResponseEntity<ExchangeProposalResponse> RequestItemExchange(@RequestBody ExchangeProposalRequest request, @AuthenticationPrincipal CustomOAuth2User principal){
        ExchangeProposalResponse exchangeProposalResponse = exchangeProposalAggregateService.proposeExchange(request,principal);
        return ResponseEntity.ok(exchangeProposalResponse);
    }



}
