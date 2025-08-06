package com.weshare.server.groupbuy.service.post;

import com.weshare.server.groupbuy.dto.GroupBuyPostCreateRequest;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;

public interface GroupBuyPostService {
    GroupBuyPost createGroupBuyPost(GroupBuyPostCreateRequest request, CustomOAuth2User principal);
}
