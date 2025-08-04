package com.weshare.server.exchange.candidate.service.post;

import com.weshare.server.category.entity.Category;
import com.weshare.server.category.exception.CategoryException;
import com.weshare.server.category.exception.CategoryExceptions;
import com.weshare.server.category.repository.CategoryRepository;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidateStatus;
import com.weshare.server.exchange.candidate.exception.ExchangeCandidatePostException;
import com.weshare.server.exchange.candidate.exception.ExchangeCandidatePostExceptions;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ItemCondition;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.exception.post.ExchangePostExceptions;
import com.weshare.server.exchange.candidate.dto.ExchangeCandidateRequest;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.exchange.candidate.repository.ExchangeCandidatePostRepository;
import com.weshare.server.exchange.proposal.entity.ExchangeProposal;
import com.weshare.server.exchange.proposal.repository.ExchangeProposalRepository;
import com.weshare.server.exchange.repository.ExchangePostRepository;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExchangeCandidatePostServiceImpl implements ExchangeCandidatePostService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExchangePostRepository exchangePostRepository;
    private final ExchangeCandidatePostRepository exchangeCandidatePostRepository;
    private final ExchangeProposalRepository exchangeProposalRepository;
    @Override
    @Transactional
    public ExchangeCandidatePost createExchangeCandidatePost(ExchangeCandidateRequest request, CustomOAuth2User principal) {
        //작성자 찾기
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()->new UserException(UserExceptions.USER_NOT_FOUND));
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new CategoryException(CategoryExceptions.NOT_EXIST_CATEGORY_ID));
        ItemCondition itemCondition = ItemCondition.stringToEnum(request.getItemCondition());

        ExchangeCandidatePost exchangeCandidatePost = ExchangeCandidatePost.builder()
                .itemName(request.getItemName())
                .itemDescription(request.getItemDescription())
                .itemCondition(itemCondition)
                .user(user)
                .category(category)
                .exchangeCandidateStatus(ExchangeCandidateStatus.AVAILABLE)
                .build();

        return exchangeCandidatePostRepository.save(exchangeCandidatePost);
    }

    // 공개 물품교환 게시글 ID를 기준으로 해당 게시글에 들어온 물품교환 후보 리스트를 찾아 리턴함
    @Override
    @Transactional
    public List<ExchangeCandidatePost> getAllExchangeCandidatePost(Long exchangePostId) {
        // 공개 물품교환 게시글 찾기
        ExchangePost exchangePost = exchangePostRepository.findById(exchangePostId).orElseThrow(()-> new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST));
        // 해당 공개 물품교환 게시글에 작성된 물품교환 요청 찾기
        List<ExchangeProposal> exchangeProposalList = exchangeProposalRepository.findAllByExchangePost(exchangePost);
        // 물품교환 요청으로 들어온 교환 후보 리스트 찾기
        List<ExchangeCandidatePost> exchangeCandidatePostList = new ArrayList<>();
        for(ExchangeProposal exchangeProposal : exchangeProposalList){
            if(Objects.equals(exchangeProposal.getExchangePost().getId(), exchangePost.getId())){
                exchangeCandidatePostList.add(exchangeProposal.getExchangeCandidatePost());
            }
        }
        return exchangeCandidatePostList;
    }

    @Override
    @Transactional
    public ExchangeCandidatePost findByExchangeCandidateId(Long exchangeCandidateId) {
        ExchangeCandidatePost exchangeCandidatePost = exchangeCandidatePostRepository.findById(exchangeCandidateId).orElseThrow(()-> new ExchangeCandidatePostException(ExchangeCandidatePostExceptions.NOT_EXIST_EXCHANGE_CANDIDATE_POST));
        return exchangeCandidatePost;
    }

    @Override
    @Transactional
    public List<ExchangeCandidatePost> findAllByExchangeCandidateId(List<Long> exchangeCandidateIdList) {
        List<ExchangeCandidatePost> exchangeCandidatePostList = new ArrayList<>();
        for(Long exchangeCandidateId : exchangeCandidateIdList){
            ExchangeCandidatePost exchangeCandidatePost = exchangeCandidatePostRepository.findById(exchangeCandidateId).orElseThrow(()-> new ExchangeCandidatePostException(ExchangeCandidatePostExceptions.NOT_EXIST_EXCHANGE_CANDIDATE_POST));
            exchangeCandidatePostList.add(exchangeCandidatePost);
        }
        return exchangeCandidatePostList;
    }

    @Override
    @Transactional
    // 특정 사용자가 등록한 교환 후보 게시글 목록 조회
    public List<ExchangeCandidatePost> getAllUserEnrolledExchangeCandidatePost(Long exchangePostId, CustomOAuth2User principal) {
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()->new UserException(UserExceptions.USER_NOT_FOUND));
        return exchangeCandidatePostRepository.findAllCandidatePostsByExchangeCandidateStatusAndUser(ExchangeCandidateStatus.AVAILABLE,user);
    }

    @Override
    @Transactional
    //해당 후보품의 상태를 TRADED 로 변경
    public ExchangeCandidatePost changeCandidateStatusToClosed(ExchangeCandidatePost exchangeCandidatePost) {
        return exchangeCandidatePost.updateExchangeCandidateStatus(ExchangeCandidateStatus.TRADED);
    }
}
