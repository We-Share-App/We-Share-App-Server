package com.weshare.server.exchange.proposal.service;

import com.weshare.server.exchange.proposal.entity.ExchangeProposal;

public interface ExchangeProposalService {
    ExchangeProposal registerExchangeProposal(Long exchangePostId, Long exchangeCandidateId);
}
