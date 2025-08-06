package com.weshare.server.groupbuy.service.participant;

import com.weshare.server.groupbuy.entity.GroupBuyParticipant;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.repository.GroupBuyParticipantRepository;
import com.weshare.server.payment.PaymentStatus;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupBuyParticipantServiceImpl implements GroupBuyParticipantService{
    private final UserRepository userRepository;
    private final GroupBuyParticipantRepository groupBuyParticipantRepository;
    @Override
    public GroupBuyParticipant addParticipant(User user, GroupBuyPost groupBuyPost, Integer quantity) {
        return null;
    }

    @Override
    public GroupBuyParticipant addPostOwner(GroupBuyPost groupBuyPost, Integer quantity, CustomOAuth2User principal) {
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()-> new UserException(UserExceptions.USER_NOT_FOUND));
        GroupBuyParticipant groupBuyParticipant = GroupBuyParticipant.builder()
                .user(user)
                .groupBuyPost(groupBuyPost)
                .paymentAmount(0)
                .paymentStatus(PaymentStatus.POST_OWNER)
                .quantity(quantity)
                .build();
        return groupBuyParticipantRepository.save(groupBuyParticipant);
    }
}
