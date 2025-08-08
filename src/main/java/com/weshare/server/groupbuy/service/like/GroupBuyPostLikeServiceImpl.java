package com.weshare.server.groupbuy.service.like;

import com.weshare.server.common.entity.LikeStatus;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.entity.GroupBuyPostLike;
import com.weshare.server.groupbuy.exception.GroupBuyPostException;
import com.weshare.server.groupbuy.exception.GroupBuyPostExceptions;
import com.weshare.server.groupbuy.repository.GroupBuyPostLikeRepository;
import com.weshare.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupBuyPostLikeServiceImpl implements GroupBuyPostLikeService{
    private final GroupBuyPostLikeRepository groupBuyPostLikeRepository;
    @Override
    @Transactional
    public GroupBuyPostLike addPostLike(GroupBuyPost groupBuyPost, User user) {
        // 기존에 좋아요를 누른적이 있는지 확인
        Optional<GroupBuyPostLike> optionalGroupBuyPostLike = groupBuyPostLikeRepository.findByGroupBuyPostAndUser(groupBuyPost,user);

        // 기존에 좋아요를 누른적이 있는 사람인 경우
        if(optionalGroupBuyPostLike.isPresent()){
            GroupBuyPostLike groupBuyPostLike = optionalGroupBuyPostLike.get();
            groupBuyPostLike.updateLikeStatus(LikeStatus.ACTIVATED);
            return groupBuyPostLike;
        }

        // 기존에 좋아요를 누른적이 없는 사람인 경우
        GroupBuyPostLike groupBuyPostLike = GroupBuyPostLike.builder()
                .user(user)
                .groupBuyPost(groupBuyPost)
                .likeStatus(LikeStatus.ACTIVATED)
                .build();
        return groupBuyPostLikeRepository.save(groupBuyPostLike);
    }

    @Override
    @Transactional
    public GroupBuyPostLike deletePostLike(GroupBuyPost groupBuyPost, User user) {
        // 기존에 좋아요를 누른적이 있는지 확인
        Optional<GroupBuyPostLike> optionalGroupBuyPostLike = groupBuyPostLikeRepository.findByGroupBuyPostAndUser(groupBuyPost,user);
        // 기존에 좋아요를 누른적이 없는 사람인 경우
        if(optionalGroupBuyPostLike.isEmpty()){
            throw new GroupBuyPostException(GroupBuyPostExceptions.NOT_EXIST_GROUP_BUY_POST_LIKE);
        }
        // 기존에 좋아요를 누른적이 있는 사람인 경우
        GroupBuyPostLike groupBuyPostLike = optionalGroupBuyPostLike.get();
        groupBuyPostLike.updateLikeStatus(LikeStatus.CANCELED);

        return groupBuyPostLike;
    }
}
