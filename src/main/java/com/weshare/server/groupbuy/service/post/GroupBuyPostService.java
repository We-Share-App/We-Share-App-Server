package com.weshare.server.groupbuy.service.post;

import com.weshare.server.groupbuy.dto.GroupBuyPostCreateRequest;
import com.weshare.server.groupbuy.dto.GroupBuyPostFilterDto;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.payment.PaymentStatus;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;

import java.util.List;

public interface GroupBuyPostService {
    GroupBuyPost createGroupBuyPost(GroupBuyPostCreateRequest request, CustomOAuth2User principal);
    GroupBuyPost updateRemainQuantity(GroupBuyPost groupBuyPost, List<PaymentStatus> paymentStatusList);

    GroupBuyPost findPostById(Long id);

    Long getLikeCount(GroupBuyPost groupBuyPost);
    Boolean isUserLikedPost(GroupBuyPost groupBuyPost,CustomOAuth2User principal);
    Boolean isPostWriter(GroupBuyPost groupBuyPost,CustomOAuth2User principal);

    List<GroupBuyPost> getFilteredGroupBuyPost(GroupBuyPostFilterDto request);
    Integer countParticipants(GroupBuyPost groupBuyPost);
}
