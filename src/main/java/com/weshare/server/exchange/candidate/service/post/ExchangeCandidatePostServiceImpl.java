package com.weshare.server.exchange.candidate.service.post;

import com.weshare.server.category.entity.Category;
import com.weshare.server.category.exception.CategoryException;
import com.weshare.server.category.exception.CategoryExceptions;
import com.weshare.server.category.repository.CategoryRepository;
import com.weshare.server.exchange.candidate.exception.ExchangeCandidatePostException;
import com.weshare.server.exchange.candidate.exception.ExchangeCandidatePostExceptions;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ItemCondition;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.exception.post.ExchangePostExceptions;
import com.weshare.server.exchange.candidate.dto.ExchangeCandidateRequest;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.exchange.candidate.repository.ExchangeCandidatePostRepository;
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

@Service
@RequiredArgsConstructor
public class ExchangeCandidatePostServiceImpl implements ExchangeCandidatePostService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ExchangePostRepository exchangePostRepository;
    private final ExchangeCandidatePostRepository exchangeCandidatePostRepository;
    @Override
    @Transactional
    public ExchangeCandidatePost createExchangeCandidatePost(ExchangeCandidateRequest request, CustomOAuth2User principal) {
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()->new UserException(UserExceptions.USER_NOT_FOUND));
        ExchangePost exchangePost = exchangePostRepository.findById(request.getExchangePostId()).orElseThrow(()->new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST));
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new CategoryException(CategoryExceptions.NOT_EXIST_CATEGORY_ID));
        ItemCondition itemCondition = ItemCondition.stringToEnum(request.getItemCondition());

        ExchangeCandidatePost exchangeCandidatePost = ExchangeCandidatePost.builder()
                .itemName(request.getItemName())
                .itemDescription(request.getItemDescription())
                .itemCondition(itemCondition)
                .user(user)
                .exchangePost(exchangePost)
                .location(exchangePost.getLocation())
                .category(category)
                .build();

        return exchangeCandidatePostRepository.save(exchangeCandidatePost);
    }

    @Override
    @Transactional
    public List<ExchangeCandidatePost> getAllExchangeCandidatePost(Long exchangePostId) {
        ExchangePost exchangePost = exchangePostRepository.findById(exchangePostId).orElseThrow(()-> new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST));
        List<ExchangeCandidatePost> exchangeCandidatePostList = exchangeCandidatePostRepository.findAllByExchangePost(exchangePost);
        return exchangeCandidatePostList;
    }

    @Override
    @Transactional
    public ExchangeCandidatePost findByExchangeCandidateId(Long exchangeCandidateId) {
        ExchangeCandidatePost exchangeCandidatePost = exchangeCandidatePostRepository.findById(exchangeCandidateId).orElseThrow(()-> new ExchangeCandidatePostException(ExchangeCandidatePostExceptions.NOT_EXIST_EXCHANGE_CANDIDATE_POST));
        return exchangeCandidatePost;
    }

    @Override
    public List<ExchangeCandidatePost> findAllByExchangeCandidateId(List<Long> exchangeCandidateIdList) {
        List<ExchangeCandidatePost> exchangeCandidatePostList = new ArrayList<>();
        for(Long exchangeCandidateId : exchangeCandidateIdList){
            ExchangeCandidatePost exchangeCandidatePost = exchangeCandidatePostRepository.findById(exchangeCandidateId).orElseThrow(()-> new ExchangeCandidatePostException(ExchangeCandidatePostExceptions.NOT_EXIST_EXCHANGE_CANDIDATE_POST));
            exchangeCandidatePostList.add(exchangeCandidatePost);
        }
        return exchangeCandidatePostList;
    }
}
