package com.weshare.server.groupbuy.service.participant;

import com.weshare.server.groupbuy.entity.GroupBuyParticipant;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;

import java.util.Optional;

public interface GroupBuyParticipantService {
    GroupBuyParticipant addParticipant(User user, GroupBuyPost groupBuyPost,Integer quantity);
    GroupBuyParticipant addPostOwner(GroupBuyPost groupBuyPost, Integer quantity, CustomOAuth2User principal);

    Optional<GroupBuyParticipant> findByGroupBuyPostAndUser(GroupBuyPost groupBuyPost, User user);

    GroupBuyParticipant enrollGroupBuyParticipant(GroupBuyPost groupBuyPost, User user, Integer Amount);
}
