package com.weshare.server.exchange.candidate.service.image;

import com.weshare.server.exchange.candidate.repository.ExchangeCandidatePostImageRepository;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePostImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeCandidatePostImageServiceImpl implements ExchangeCandidatePostImageService {
    private final ExchangeCandidatePostImageRepository exchangeCandidatePostImageRepository;

    @Override
    @Transactional
    public ExchangeCandidatePostImage saveImageKey(String imgKey, ExchangeCandidatePost exchangeCandidatePost) {
        ExchangeCandidatePostImage exchangeCandidatePostImage = ExchangeCandidatePostImage.builder()
                .exchangeCandidatePostImageKey(imgKey)
                .exchangeCandidatePost(exchangeCandidatePost)
                .build();
        return exchangeCandidatePostImageRepository.save(exchangeCandidatePostImage);
    }

    @Override
    @Transactional
    public List<String> getImageKey(ExchangeCandidatePost exchangeCandidatePost) {
        // 해당 물품교환 제안 게시글에 등록된 모든 이미지 엔티티 가져오기
        List<ExchangeCandidatePostImage> exchangeCandidatePostImageList = exchangeCandidatePostImageRepository.findAllByExchangeCandidatePost(exchangeCandidatePost);
        // 이미지 키 추출
        List<String> keyList = new ArrayList<>();
        for(ExchangeCandidatePostImage exchangeCandidatePostImage : exchangeCandidatePostImageList){
            String key = exchangeCandidatePostImage.getExchangeCandidatePostImageKey();
            keyList.add(key);
        }
        return keyList;
    }
}
