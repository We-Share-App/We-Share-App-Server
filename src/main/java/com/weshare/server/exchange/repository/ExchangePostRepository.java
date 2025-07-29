package com.weshare.server.exchange.repository;

import com.weshare.server.exchange.entity.ExchangePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExchangePostRepository extends JpaRepository<ExchangePost,Long>, JpaSpecificationExecutor<ExchangePost> {
}
