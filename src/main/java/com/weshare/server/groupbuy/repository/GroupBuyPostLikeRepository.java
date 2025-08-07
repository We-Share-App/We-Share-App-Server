package com.weshare.server.groupbuy.repository;

import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.entity.GroupBuyPostLike;
import com.weshare.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupBuyPostLikeRepository extends JpaRepository<GroupBuyPostLike,Long> {
    Long countByGroupBuyPost(GroupBuyPost groupBuyPost);
    Boolean existsByUserAndGroupBuyPost(User user, GroupBuyPost groupBuyPost);
}
