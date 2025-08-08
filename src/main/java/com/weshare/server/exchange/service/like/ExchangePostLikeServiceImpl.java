package com.weshare.server.exchange.service.like;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostLike;
import com.weshare.server.common.entity.LikeStatus;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.exception.post.ExchangePostExceptions;
import com.weshare.server.exchange.repository.ExchangePostLikeRepository;
import com.weshare.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangePostLikeServiceImpl implements ExchangePostLikeService{
    private final ExchangePostLikeRepository exchangePostLikeRepository;
    @Override
    @Transactional
    public ExchangePostLike addPostLike(ExchangePost exchangePost, User user) {
        // 기존에 좋아요를 누른적이 있는지 확인
        Optional<ExchangePostLike> optionalExchangePostLike = exchangePostLikeRepository.findByExchangePostAndUser(exchangePost,user);

        // 기존에 좋아요를 누른적이 있는 사람인 경우
        if(optionalExchangePostLike.isPresent()){
           ExchangePostLike exchangePostLike = optionalExchangePostLike.get();
           exchangePostLike.updateLikeStatus(LikeStatus.ACTIVATED);
           return  exchangePostLike;
        }

        // 기존에 좋아요를 누른적이 없는 사람인 경우
        ExchangePostLike exchangePostLike = ExchangePostLike.builder()
                .user(user)
                .exchangePost(exchangePost)
                .likeStatus(LikeStatus.ACTIVATED)
                .build();
        return exchangePostLikeRepository.save(exchangePostLike);
    }

    @Override
    @Transactional
    public ExchangePostLike deletePostLike(ExchangePost exchangePost, User user) {
        // 기존에 좋아요를 누른적이 있는지 확인
        Optional<ExchangePostLike> optionalExchangePostLike = exchangePostLikeRepository.findByExchangePostAndUser(exchangePost,user);
        // 기존에 좋아요를 누른적이 없는 사람인 경우
        if(optionalExchangePostLike.isEmpty()){
            throw new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST_LIKE);
        }
        // 기존에 좋아요를 누른적이 있는 사람인 경우
        ExchangePostLike exchangePostLike = optionalExchangePostLike.get();
        exchangePostLike.updateLikeStatus(LikeStatus.CANCELED);

        return exchangePostLike;
    }
}
