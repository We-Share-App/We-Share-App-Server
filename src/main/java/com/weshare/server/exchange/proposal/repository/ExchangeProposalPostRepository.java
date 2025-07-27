package com.weshare.server.exchange.proposal.repository;

import com.weshare.server.exchange.proposal.entity.ExchangeProposalPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeProposalPostRepository extends JpaRepository<ExchangeProposalPost,Long> {
}
