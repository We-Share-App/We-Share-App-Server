package com.weshare.server.exchange.candidate.repository;

import com.weshare.server.exchange.candidate.entity.ExchangeCandidateStatus;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExchangeCandidatePostRepository extends JpaRepository<ExchangeCandidatePost,Long> {
    @Query("""
        SELECT c
          FROM ExchangeCandidatePost c
         WHERE c.exchangeCandidateStatus = :candidateStatus
           AND c.user                = :requestingUser
    """)
    List<ExchangeCandidatePost> findAllCandidatePostsByExchangeCandidateStatusAndUser(@Param("candidateStatus") ExchangeCandidateStatus candidateStatus, @Param("requestingUser") User requestingUser
    );
}
