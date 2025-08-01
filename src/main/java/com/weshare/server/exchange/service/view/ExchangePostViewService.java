package com.weshare.server.exchange.service.view;

import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;

public interface ExchangePostViewService {
    Long updateViewCount(Long ExchangePostId, CustomOAuth2User principal);
}
