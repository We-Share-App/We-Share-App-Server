package com.weshare.server.exchange.proposal.repository;

import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidateStatus;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.proposal.entity.ExchangeProposal;
import com.weshare.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ExchangeProposalRepository extends JpaRepository<ExchangeProposal,Long> {
    List<ExchangeProposal> findAllByExchangePost(ExchangePost exchangePost);
    Boolean existsByExchangePostIdAndExchangeCandidatePostId(Long exchangePostId, Long exchangeCandidateId);

    @Query(" select proposal.exchangeCandidatePost.id from ExchangeProposal proposal where proposal.exchangePost.id = :targetExchangePostId and proposal.exchangeCandidatePost.id in :exchangeCandidatePostIds ")
    List<Long> findAlreadyProposedCandidatePostIds(@Param("targetExchangePostId") Long targetExchangePostId, @Param("exchangeCandidatePostIds") Collection<Long> exchangeCandidatePostIds
    );

//    @Query(" select proposal.exchangeCandidatePost from ExchangeProposal proposal where proposal.exchangePost = :targetExchangePost and proposal.exchangeCandidatePost.user = :requestingUser ")
//    List<ExchangeCandidatePost> findAllCandidatePostsByPostAndCandidateOwner(@Param("targetExchangePost") ExchangePost targetExchangePost, @Param("requestingUser") User requestingUser);
}
