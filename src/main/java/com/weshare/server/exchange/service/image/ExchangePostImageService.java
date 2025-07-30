package com.weshare.server.exchange.service.image;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostImage;

import java.util.List;

public interface ExchangePostImageService {
    ExchangePostImage saveImageKey (String key, ExchangePost exchangePost);
    List<String> getImageKey(ExchangePost exchangePost);
}
