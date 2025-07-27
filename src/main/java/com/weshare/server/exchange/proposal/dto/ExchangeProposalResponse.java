package com.weshare.server.exchange.proposal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeProposalResponse {
    private Boolean isSuccess;
    private Long exchangeProposalPostId;
}
