package com.weshare.server.exchange.repository;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangePostCategoryRepository extends JpaRepository<ExchangePostCategory,Long> {
    List<ExchangePostCategory> findAllByExchangePost(ExchangePost exchangePost);
}
