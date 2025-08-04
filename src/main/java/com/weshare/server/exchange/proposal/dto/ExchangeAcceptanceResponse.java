package com.weshare.server.exchange.proposal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ExchangeAcceptanceResponse {
    private Long updatedExchangePostId;
    private String updatedExchangePostStatus;
    private Long updatedExchangeCandidatePostId;
    private String updatedExchangeCandidatePostStatus;
    private Long updatedExchangeProposalId;
    private String updatedExchangeProposalStatus;

    @Builder
    public ExchangeAcceptanceResponse(Long updatedExchangePostId, String updatedExchangePostStatus, Long updatedExchangeCandidatePostId, String updatedExchangeCandidatePostStatus, Long updatedExchangeProposalId, String updatedExchangeProposalStatus) {
        this.updatedExchangePostId = updatedExchangePostId;
        this.updatedExchangePostStatus = updatedExchangePostStatus;
        this.updatedExchangeCandidatePostId = updatedExchangeCandidatePostId;
        this.updatedExchangeCandidatePostStatus = updatedExchangeCandidatePostStatus;
        this.updatedExchangeProposalId = updatedExchangeProposalId;
        this.updatedExchangeProposalStatus = updatedExchangeProposalStatus;
    }
}
