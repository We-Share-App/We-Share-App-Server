package com.weshare.server.groupbuy.repository;

import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.entity.GroupBuyPostLike;
import com.weshare.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupBuyPostLikeRepository extends JpaRepository<GroupBuyPostLike,Long> {
    @Query("""
        select count(g)
        from GroupBuyPostLike g
        where g.groupBuyPost = :post
          and g.likeStatus = com.weshare.server.common.entity.LikeStatus.ACTIVATED
    """)
    Long countActiveGroupBuyPostLike(@Param("post") GroupBuyPost groupBuyPost);

    @Query("""
    select (count(g) > 0)
    from GroupBuyPostLike g
    where g.user = :user
      and g.groupBuyPost = :post
      and g.likeStatus = com.weshare.server.common.entity.LikeStatus.ACTIVATED
    """)
    Boolean existsActiveLikeByUserAndGroupBuyPost(@Param("user") User user, @Param("post") GroupBuyPost groupBuyPost);

    Optional<GroupBuyPostLike>findByGroupBuyPostAndUser(GroupBuyPost groupBuyPost, User user);
}
