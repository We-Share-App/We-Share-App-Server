package com.weshare.server.exchange.proposal.service.image;

import com.weshare.server.exchange.proposal.repository.ExchangeProposalPostImageRepository;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPost;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPostImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
