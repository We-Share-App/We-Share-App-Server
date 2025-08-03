package com.weshare.server.exchange.proposal.service;

import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.exchange.candidate.exception.ExchangeCandidatePostException;
import com.weshare.server.exchange.candidate.exception.ExchangeCandidatePostExceptions;
import com.weshare.server.exchange.candidate.repository.ExchangeCandidatePostRepository;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.exception.post.ExchangePostExceptions;
import com.weshare.server.exchange.proposal.entity.ExchangeProposal;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalStatus;
import com.weshare.server.exchange.proposal.repository.ExchangeProposalRepository;
import com.weshare.server.exchange.repository.ExchangePostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeProposalServiceImpl implements ExchangeProposalService{
    private final ExchangePostRepository exchangePostRepository;
    private final ExchangeCandidatePostRepository exchangeCandidatePostRepository;
    private final ExchangeProposalRepository exchangeProposalRepository;

    @Override
    public ExchangeProposal registerExchangeProposal(Long exchangePostId, Long exchangeCandidateId) {
        // 공개 교환 게시글 조회
        ExchangePost exchangePost = exchangePostRepository.findById(exchangePostId).orElseThrow(()->new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST));
        // 교환 후보 조회
        ExchangeCandidatePost exchangeCandidatePost = exchangeCandidatePostRepository.findById(exchangeCandidateId).orElseThrow(()->new ExchangeCandidatePostException(ExchangeCandidatePostExceptions.NOT_EXIST_EXCHANGE_CANDIDATE_POST));
        // 물품교환 등록
        ExchangeProposal exchangeProposal = ExchangeProposal.builder()
                .exchangePost(exchangePost)
                .exchangeCandidatePost(exchangeCandidatePost)
                .exchangeProposalStatus(ExchangeProposalStatus.PENDING) // 거래중 상태로 설정
                .build();
        return exchangeProposalRepository.save(exchangeProposal);
    }
}
