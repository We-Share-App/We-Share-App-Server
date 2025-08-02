package com.weshare.server.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExchangePostResponse {
    private ExchangePostDto exchangePostDto;
    private Integer totalCandidatePostCount;
    private List<ExchangeCandidatePostDto> exchangeCandidatePostDtoList;
}
