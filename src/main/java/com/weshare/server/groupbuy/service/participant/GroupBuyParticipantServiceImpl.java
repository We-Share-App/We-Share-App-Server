package com.weshare.server.groupbuy.service.participant;

import com.weshare.server.groupbuy.entity.GroupBuyParticipant;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.exception.GroupBuyPostException;
import com.weshare.server.groupbuy.exception.GroupBuyPostExceptions;
import com.weshare.server.groupbuy.repository.GroupBuyParticipantRepository;
import com.weshare.server.groupbuy.repository.GroupBuyPostRepository;
import com.weshare.server.payment.PaymentStatus;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupBuyParticipantServiceImpl implements GroupBuyParticipantService{
    private final UserRepository userRepository;
    private final GroupBuyPostRepository groupBuyPostRepository;
    private final GroupBuyParticipantRepository groupBuyParticipantRepository;
    @Override
    public GroupBuyParticipant addParticipant(User user, GroupBuyPost groupBuyPost, Integer quantity) {
        return null;
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public Optional<GroupBuyParticipant> findByGroupBuyPostAndUser(GroupBuyPost groupBuyPost, User user) {
        return groupBuyParticipantRepository.findByGroupBuyPostAndUser(groupBuyPost, user);
    }

    @Override
    @Transactional
    public GroupBuyParticipant enrollGroupBuyParticipant(GroupBuyPost groupBuyPost, User user, Integer amount) {
        // 1) 락 걸고 최신 상태로 재조회
        GroupBuyPost post = groupBuyPostRepository.findByIdWithLock(groupBuyPost.getId()).orElseThrow(() -> new GroupBuyPostException(GroupBuyPostExceptions.NOT_EXIST_GROUP_POST));

        // 2) 남은 수량 체크
        if(post.getRemainQuantity() < amount){
            throw new GroupBuyPostException(GroupBuyPostExceptions.INSUFFICIENT_REMAIN_QUANTITY);
        }
        GroupBuyParticipant participant = GroupBuyParticipant.builder()
                .quantity(amount)
                .paymentAmount(post.getItemPrice() * amount)
                .paymentStatus(PaymentStatus.PAID)  // 임시로 PAID
                .user(user)
                .groupBuyPost(post)
                .build();

        // 4) 재고 차감 (Transaction 커밋 시점에 JPA가 변경 감지해서 UPDATE 쿼리 날림)
        try {
            groupBuyParticipantRepository.save(participant);
        } catch (DataIntegrityViolationException e) {
            throw new GroupBuyPostException(GroupBuyPostExceptions.ALREADY_GROUP_BUY_PARTICIPANT_USER);
        }
        post.updateRemainQuantity(post.getRemainQuantity() - amount);

        return participant;
    }
}
