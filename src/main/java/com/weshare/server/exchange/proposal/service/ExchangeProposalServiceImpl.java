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
import com.weshare.server.exchange.proposal.exception.ExchangeProposalException;
import com.weshare.server.exchange.proposal.exception.ExchangeProposalExceptions;
import com.weshare.server.exchange.proposal.repository.ExchangeProposalRepository;
import com.weshare.server.exchange.repository.ExchangePostRepository;
import com.weshare.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExchangeProposalServiceImpl implements ExchangeProposalService{
    private final ExchangePostRepository exchangePostRepository;
    private final ExchangeCandidatePostRepository exchangeCandidatePostRepository;
    private final ExchangeProposalRepository exchangeProposalRepository;

    @Override
    @Transactional
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

    @Override
    @Transactional
    public Boolean isAlreadyProposedCandidate(ExchangePost exchangePost, ExchangeCandidatePost exchangeCandidatePost) {
        return exchangeProposalRepository.existsByExchangePostIdAndExchangeCandidatePostId(exchangePost.getId(),exchangeCandidatePost.getId());
    }

    @Override
    @Transactional
    public List<Long> findAlreadyProposedCandidatePostIdList(Long targetExchangePostId, Collection<Long> exchangeCandidatePostIds) {
        return exchangeProposalRepository.findAlreadyProposedCandidatePostIds(targetExchangePostId,exchangeCandidatePostIds);
    }

    @Override
    @Transactional
    public ExchangeProposal changeRelatedAllProposalsStatusToAcceptedAndRejected(ExchangePost exchangePost, ExchangeCandidatePost targetExchangeCandidatePost) {
        List<ExchangeProposal>exchangeProposalList = exchangeProposalRepository.findAllByExchangePost(exchangePost);
        ExchangeProposal accpetedExchangeProposal = null;
        for(ExchangeProposal exchangeProposal : exchangeProposalList){
            //교환이 수락된 물품이 담긴 ExchangeProposal 인스턴스인 경우
            if(Objects.equals(exchangeProposal.getExchangeCandidatePost().getId(), targetExchangeCandidatePost.getId())){
                exchangeProposal.updateExchangeProposalStatus(ExchangeProposalStatus.ACCEPTED);
                accpetedExchangeProposal = exchangeProposal;
            }
            // 교환이 수락된 물품이 아닌 나머지 물품인 경우
            else{
                exchangeProposal.updateExchangeProposalStatus(ExchangeProposalStatus.REJECTED);
            }
        }

        if(accpetedExchangeProposal==null){
            throw new ExchangeProposalException(ExchangeProposalExceptions.FAILURE_FOR_ACCEPT_EXCHANGE_TARGET_SEARCH);
        }
        return accpetedExchangeProposal;
    }
}
