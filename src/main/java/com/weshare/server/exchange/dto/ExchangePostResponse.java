package com.weshare.server.exchange.dto;

import com.weshare.server.exchange.candidate.dto.ExchangeCandidatePostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExchangePostResponse {
    private ExchangePostDto exchangePostDto;
    private Integer totalExchangeCandidatePostCount;
    private List<ExchangeCandidatePostDto> exchangeCandidatePostDtoList;
}
