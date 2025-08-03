package com.weshare.server.exchange.candidate.repository;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeCandidatePostRepository extends JpaRepository<ExchangeCandidatePost,Long> {
}
