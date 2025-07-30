package com.weshare.server.exchange.service.category;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostCategory;
import com.weshare.server.user.entity.User;

import java.util.List;

public interface ExchangePostCategoryService {
    ExchangePostCategory createExchangePostCategory(Long categoryId, ExchangePost exchangePost);
    List<String> getExchangePostCategoryNameList(ExchangePost exchangePost);
}
