package com.weshare.server.groupbuy.service.post;

import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;

public interface GroupBuyPostViewService {
    Long updateViewCount(Long groupBuyPostId, CustomOAuth2User principal);
    Long getViewCount(Long groupBuyPostId);
}
