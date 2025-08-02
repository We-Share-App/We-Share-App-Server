package com.weshare.server.exchange.candidate.service.post;

import com.weshare.server.exchange.candidate.dto.ExchangeCandidateRequest;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;

import java.util.List;

public interface ExchangeCandidatePostService {
    ExchangeCandidatePost createExchangeCandidatePost(ExchangeCandidateRequest request, CustomOAuth2User principal);
    List<ExchangeCandidatePost> getAllExchangeCandidatePost(Long exchangePostId);
}
