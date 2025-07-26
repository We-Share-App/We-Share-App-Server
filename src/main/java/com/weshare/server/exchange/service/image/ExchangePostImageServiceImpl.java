package com.weshare.server.exchange.service.image;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostImage;
import com.weshare.server.exchange.repository.ExchangePostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangePostImageServiceImpl implements ExchangePostImageService{

    private final ExchangePostImageRepository exchangePostImageRepository;
    @Override
    public ExchangePostImage saveImageKey(String imgKey, ExchangePost exchangePost) {
        ExchangePostImage exchangePostImage = ExchangePostImage.builder()
                .exchangePostImageKey(imgKey)
                .exchangePost(exchangePost)
                .build();
        return  exchangePostImageRepository.save(exchangePostImage);
    }
}
