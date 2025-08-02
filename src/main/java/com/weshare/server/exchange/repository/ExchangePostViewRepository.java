package com.weshare.server.exchange.repository;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostView;
import com.weshare.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExchangePostViewRepository extends JpaRepository<ExchangePostView,Long> {
    Optional<ExchangePostView> findByUserAndExchangePost(User user, ExchangePost exchangePost);
    Long countByExchangePost(ExchangePost exchangePost);
    Boolean existsByUserAndExchangePost(User user, ExchangePost exchangePost);
}
