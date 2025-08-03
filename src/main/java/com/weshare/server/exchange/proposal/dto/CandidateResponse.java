package com.weshare.server.exchange.proposal.dto;

import com.weshare.server.exchange.candidate.dto.ExchangeCandidatePostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class CandidateResponse {
    private List<String> wishCategoryNameList;
    private Integer candidateCount;
    private List<ExchangeCandidatePostDto> exchangeCandidatePostDtoList;

    @Builder
    public CandidateResponse(List<String> wishCategoryNameList, Integer candidateCount, List<ExchangeCandidatePostDto> exchangeCandidatePostDtoList) {
        this.wishCategoryNameList = wishCategoryNameList;
        this.candidateCount = candidateCount;
        this.exchangeCandidatePostDtoList = exchangeCandidatePostDtoList;
    }
}
