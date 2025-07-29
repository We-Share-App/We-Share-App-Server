package com.weshare.server.exchange.repository;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostLike;
import com.weshare.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangePostLikeRepository extends JpaRepository<ExchangePostLike,Long> {
    Long countByExchangePost(ExchangePost exchangePost);
    Boolean existsByUserAndExchangePost(User user, ExchangePost exchangePost);
}
