package com.weshare.server.exchange.service.post;

import com.weshare.server.exchange.dto.ExchangePostCreateRequest;
import com.weshare.server.exchange.dto.ExchangePostFilterRequest;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;

import java.util.List;

public interface ExchangePostService {
    ExchangePost createExchangePost(ExchangePostCreateRequest request, CustomOAuth2User principal);
    List<ExchangePost> getFilteredExchangePost(ExchangePostFilterRequest request);
    Long getLikeCount(ExchangePost exchangePost);
    Boolean isUserLikedPost(CustomOAuth2User principal, ExchangePost exchangePost);
}
