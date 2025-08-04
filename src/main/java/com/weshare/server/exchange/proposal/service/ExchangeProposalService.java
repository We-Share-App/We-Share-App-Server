package com.weshare.server.exchange.proposal.service;

import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.proposal.entity.ExchangeProposal;
import com.weshare.server.user.entity.User;

import java.util.Collection;
import java.util.List;

public interface ExchangeProposalService {
    ExchangeProposal registerExchangeProposal(Long exchangePostId, Long exchangeCandidateId);
    Boolean isAlreadyProposedCandidate(ExchangePost exchangePost, ExchangeCandidatePost exchangeCandidatePost);

    List<Long>findAlreadyProposedCandidatePostIdList(Long targetExchangePostId, Collection<Long> exchangeCandidatePostIds);

    ExchangeProposal changeRelatedAllProposalsStatusToAcceptedAndRejected(ExchangePost exchangePost, ExchangeCandidatePost exchangeCandidatePost);
}
