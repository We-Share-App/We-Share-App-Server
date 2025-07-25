package com.weshare.server.exchange.service;

import com.weshare.server.exchange.dto.ExchangePostRequest;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;

public interface ExchangePostService {
    ExchangePost createExchangePost(ExchangePostRequest request, CustomOAuth2User principal);
}
