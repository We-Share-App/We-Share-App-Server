package com.weshare.server.exchange.service.post;

import com.weshare.server.exchange.dto.ExchangePostCreateRequest;
import com.weshare.server.exchange.dto.ExchangePostFilterDto;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;

import java.util.List;

public interface ExchangePostService {
    ExchangePost createExchangePost(ExchangePostCreateRequest request, CustomOAuth2User principal);
    List<ExchangePost> getFilteredExchangePost(ExchangePostFilterDto request);
    ExchangePost findExchangePost(Long id);
    Long getLikeCount(ExchangePost exchangePost);
    Boolean isUserLikedPost(CustomOAuth2User principal, ExchangePost exchangePost);
    Boolean isPostWriter(ExchangePost exchangePost,CustomOAuth2User principal);
    ExchangePost changeExchangePostStatusToClosed(ExchangePost exchangePost);
}
