package com.weshare.server.exchange.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeCandidateResponse {
    private Boolean isSuccess;
    private Long exchangeCandidatePostId;
}
