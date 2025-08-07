package com.weshare.server.groupbuy.service.post;

import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.entity.GroupBuyPostView;
import com.weshare.server.groupbuy.exception.GroupBuyPostException;
import com.weshare.server.groupbuy.exception.GroupBuyPostExceptions;
import com.weshare.server.groupbuy.repository.GroupBuyPostRepository;
import com.weshare.server.groupbuy.repository.GroupBuyPostViewRepository;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupBuyPostViewServiceImpl implements GroupBuyPostViewService{
    private final UserRepository userRepository;
    private final GroupBuyPostRepository groupBuyPostRepository;
    private final GroupBuyPostViewRepository groupBuyPostViewRepository;

    @Override
    @Transactional
    public Long updateViewCount(Long groupBuyPostId, CustomOAuth2User principal) {
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()-> new UserException(UserExceptions.USER_NOT_FOUND));
        GroupBuyPost groupBuyPost = groupBuyPostRepository.findById(groupBuyPostId).orElseThrow(()->new GroupBuyPostException(GroupBuyPostExceptions.NOT_EXIST_GROUP_POST));

        if(groupBuyPostViewRepository.existsByUserAndGroupBuyPost(user,groupBuyPost)){
            return groupBuyPostViewRepository.countByGroupBuyPost(groupBuyPost);
        }

        saveViewHistory(user,groupBuyPost);
        return groupBuyPostViewRepository.countByGroupBuyPost(groupBuyPost);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = DataIntegrityViolationException.class)
    public void saveViewHistory(User user, GroupBuyPost groupBuyPost){
        GroupBuyPostView view = GroupBuyPostView.builder()
                .user(user)
                .groupBuyPost(groupBuyPost)
                .build();

        try {
            groupBuyPostViewRepository.saveAndFlush(view);
        }catch (DataIntegrityViolationException ex){
            // 이미 기록됨 → 무시
        }
    }

    @Override
    @Transactional
    public Long getViewCount(Long groupBuyPostId) {
        GroupBuyPost groupBuyPost = groupBuyPostRepository.findById(groupBuyPostId).orElseThrow(()->new GroupBuyPostException(GroupBuyPostExceptions.NOT_EXIST_GROUP_POST));
        return groupBuyPostViewRepository.countByGroupBuyPost(groupBuyPost);
    }
}
