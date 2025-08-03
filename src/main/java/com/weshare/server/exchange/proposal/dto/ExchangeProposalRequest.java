package com.weshare.server.exchange.proposal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExchangeProposalRequest {
    private Long targetExchangePostId;
    private List<Long> exchangeCandidateIdList;
}
