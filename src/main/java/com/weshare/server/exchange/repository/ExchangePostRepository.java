package com.weshare.server.exchange.repository;

import com.weshare.server.exchange.entity.ExchangePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangePostRepository extends JpaRepository<ExchangePost,Long> {
}
