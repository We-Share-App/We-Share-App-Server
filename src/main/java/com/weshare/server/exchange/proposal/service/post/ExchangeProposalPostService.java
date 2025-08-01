package com.weshare.server.exchange.proposal.service.post;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.proposal.dto.ExchangeProposalRequest;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPost;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;

import java.util.List;

public interface ExchangeProposalPostService {
    ExchangeProposalPost createExchangeProposalPost(ExchangeProposalRequest request, CustomOAuth2User principal);
    List<ExchangeProposalPost> getAllExchangeProposalPost(Long exchangePostId);
}
