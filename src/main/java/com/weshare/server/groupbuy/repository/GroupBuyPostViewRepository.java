package com.weshare.server.groupbuy.repository;

import com.weshare.server.exchange.entity.ExchangePostView;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.entity.GroupBuyPostView;
import com.weshare.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupBuyPostViewRepository extends JpaRepository<GroupBuyPostView,Long> {
    Boolean existsByUserAndGroupBuyPost(User user, GroupBuyPost groupBuyPost);
    Long countByGroupBuyPost(GroupBuyPost groupBuyPost);
}
