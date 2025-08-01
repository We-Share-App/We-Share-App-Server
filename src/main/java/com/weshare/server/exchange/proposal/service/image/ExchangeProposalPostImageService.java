package com.weshare.server.exchange.proposal.service.image;

import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPost;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPostImage;

import java.util.List;

public interface ExchangeProposalPostImageService {
    ExchangeProposalPostImage saveImageKey(String key, ExchangeProposalPost exchangeProposalPost);
    List<String> getImageKey(ExchangeProposalPost exchangeProposalPost);
}
