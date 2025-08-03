package com.weshare.server.exchange.proposal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExchangeProposalResponse {
    private Boolean isSuccess;
    private List<Long> exchangeProposalIdList;
    private Long exchangePostId;
    private List<Long> exchangeCandidateIdList;
}
