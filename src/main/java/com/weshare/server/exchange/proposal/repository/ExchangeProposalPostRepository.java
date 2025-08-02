package com.weshare.server.exchange.proposal.repository;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeProposalPostRepository extends JpaRepository<ExchangeProposalPost,Long> {
    List<ExchangeProposalPost>findAllByExchangePost(ExchangePost exchangePost);
}
