package com.weshare.server.exchange.service.image;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostImage;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.repository.ExchangePostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangePostImageServiceImpl implements ExchangePostImageService{

    private final ExchangePostImageRepository exchangePostImageRepository;
    @Override
    @Transactional
    public ExchangePostImage saveImageKey(String imgKey, ExchangePost exchangePost) {
        ExchangePostImage exchangePostImage = ExchangePostImage.builder()
                .exchangePostImageKey(imgKey)
                .exchangePost(exchangePost)
                .build();
        return  exchangePostImageRepository.save(exchangePostImage);
    }

    @Override
    @Transactional
    public List<String> getImageKey(ExchangePost exchangePost) {
        // 해당 물품교환 게시글에 등록된 모든 이미지 엔티티 가져오기
        List<ExchangePostImage> exchangePostImageList = exchangePostImageRepository.findAllByExchangePost(exchangePost);

        // 이미지 키 추출
        List<String> keyList = new ArrayList<>();
        for(ExchangePostImage exchangePostImage : exchangePostImageList){
            String key = exchangePostImage.getExchangePostImageKey();
            keyList.add(key);
        }

        return keyList;
    }
}
