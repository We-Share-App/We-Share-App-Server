package com.weshare.server.exchange.proposal.repository;

import com.weshare.server.exchange.proposal.entity.ExchangeProposalPost;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeProposalPostImageRepository extends JpaRepository<ExchangeProposalPostImage,Long> {
    List<ExchangeProposalPostImage> findAllByExchangeProposalPost(ExchangeProposalPost exchangeProposalPost);
}
