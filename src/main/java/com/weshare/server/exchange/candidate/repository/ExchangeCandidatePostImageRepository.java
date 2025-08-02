package com.weshare.server.exchange.candidate.repository;

import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeCandidatePostImageRepository extends JpaRepository<ExchangeCandidatePostImage,Long> {
    List<ExchangeCandidatePostImage> findAllByExchangeCandidatePost(ExchangeCandidatePost exchangeCandidatePost);
}
