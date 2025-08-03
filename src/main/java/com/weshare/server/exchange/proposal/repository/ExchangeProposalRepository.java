package com.weshare.server.exchange.proposal.repository;

import com.weshare.server.exchange.proposal.entity.ExchangeProposal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeProposalRepository extends JpaRepository<ExchangeProposal,Long> {
}
