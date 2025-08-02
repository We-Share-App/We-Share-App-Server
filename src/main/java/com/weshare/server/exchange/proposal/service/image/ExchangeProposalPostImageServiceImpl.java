package com.weshare.server.exchange.proposal.service.image;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostImage;
import com.weshare.server.exchange.proposal.repository.ExchangeProposalPostImageRepository;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPost;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPostImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeProposalPostImageServiceImpl implements ExchangeProposalPostImageService{
    private final ExchangeProposalPostImageRepository exchangeProposalPostImageRepository;

    @Override
    @Transactional
    public ExchangeProposalPostImage saveImageKey(String imgKey, ExchangeProposalPost exchangeProposalPost) {
        ExchangeProposalPostImage exchangeProposalPostImage = ExchangeProposalPostImage.builder()
                .exchangeProposalPostImageKey(imgKey)
                .exchangeProposalPost(exchangeProposalPost)
                .build();
        return exchangeProposalPostImageRepository.save(exchangeProposalPostImage);
    }

    @Override
    @Transactional
    public List<String> getImageKey(ExchangeProposalPost exchangeProposalPost) {
        // 해당 물품교환 제안 게시글에 등록된 모든 이미지 엔티티 가져오기
        List<ExchangeProposalPostImage> exchangeProposalPostImageList = exchangeProposalPostImageRepository.findAllByExchangeProposalPost(exchangeProposalPost);
        // 이미지 키 추출
        List<String> keyList = new ArrayList<>();
        for(ExchangeProposalPostImage exchangeProposalPostImage : exchangeProposalPostImageList){
            String key = exchangeProposalPostImage.getExchangeProposalPostImageKey();
            keyList.add(key);
        }
        return keyList;
    }
}
