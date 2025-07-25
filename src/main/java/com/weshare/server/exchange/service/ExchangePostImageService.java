package com.weshare.server.exchange.service;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostImage;

public interface ExchangePostImageService {
    ExchangePostImage saveImageKey (String key, ExchangePost exchangePost);
}
