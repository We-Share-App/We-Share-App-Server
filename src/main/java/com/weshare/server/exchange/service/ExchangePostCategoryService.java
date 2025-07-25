package com.weshare.server.exchange.service;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostCategory;
import com.weshare.server.user.entity.User;

public interface ExchangePostCategoryService {
    ExchangePostCategory createExchangePostCategory(Long categoryId, ExchangePost exchangePost);
}
