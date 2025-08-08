package com.weshare.server.exchange.repository;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostLike;
import com.weshare.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExchangePostLikeRepository extends JpaRepository<ExchangePostLike,Long> {
    @Query("""
        select count(e)
        from ExchangePostLike e
        where e.exchangePost = :post
          and e.likeStatus = com.weshare.server.common.entity.LikeStatus.ACTIVATED
    """)
    Long countActiveExchangePostLike(@Param("post") ExchangePost post);

    @Query("""
    select (count(e) > 0)
    from ExchangePostLike e
    where e.user = :user
      and e.exchangePost = :post
      and e.likeStatus = com.weshare.server.common.entity.LikeStatus.ACTIVATED
    """)
    Boolean existsActiveLikeByUserAndExchangePost(@Param("user") User user, @Param("post") ExchangePost post);

    Optional<ExchangePostLike>findByExchangePostAndUser(ExchangePost exchangePost, User user);
}
