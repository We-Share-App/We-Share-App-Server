package com.weshare.server.exchange.service.like;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostLike;
import com.weshare.server.user.entity.User;

public interface ExchangePostLikeService {

    ExchangePostLike addPostLike(ExchangePost exchangePost, User user);
    ExchangePostLike deletePostLike(ExchangePost exchangePost, User user);
}
