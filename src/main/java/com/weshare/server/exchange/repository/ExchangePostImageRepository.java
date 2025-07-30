package com.weshare.server.exchange.repository;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangePostImageRepository extends JpaRepository<ExchangePostImage,Long> {
    List<ExchangePostImage> findAllByExchangePost(ExchangePost exchangePost);
}
