package com.weshare.server.exchange.service.view;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostView;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.exception.post.ExchangePostExceptions;
import com.weshare.server.exchange.repository.ExchangePostRepository;
import com.weshare.server.exchange.repository.ExchangePostViewRepository;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ExchangePostViewServiceImpl implements ExchangePostViewService{
    private final UserRepository userRepository;
    private final ExchangePostRepository exchangePostRepository;
    private final ExchangePostViewRepository exchangePostViewRepository;
    @Override
    public Long updateViewCount(Long exchangePostId, CustomOAuth2User principal) {
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()-> new UserException(UserExceptions.USER_NOT_FOUND));
        ExchangePost exchangePost = exchangePostRepository.findById(exchangePostId).orElseThrow(()->new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST));

        if (exchangePostViewRepository.existsByUserAndExchangePost(user, exchangePost)) {
            return exchangePostViewRepository.countByExchangePost(exchangePost);
        }

        saveViewHistory(user, exchangePost);
        return exchangePostViewRepository.countByExchangePost(exchangePost);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = DataIntegrityViolationException.class)
    public void saveViewHistory(User user, ExchangePost post) {
        ExchangePostView view = ExchangePostView.builder()
                .user(user)
                .exchangePost(post)
                .build();

        try {
            exchangePostViewRepository.saveAndFlush(view);
        } catch (DataIntegrityViolationException ex) {
            // 이미 기록됨 → 무시
        }
    }

    @Override
    public Long getViewCount(Long exchangePostId) {
        ExchangePost exchangePost = exchangePostRepository.findById(exchangePostId).orElseThrow(()->new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST));
        return exchangePostViewRepository.countByExchangePost(exchangePost);
    }
}
