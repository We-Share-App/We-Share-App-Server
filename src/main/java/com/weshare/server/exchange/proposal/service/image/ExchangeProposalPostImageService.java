package com.weshare.server.exchange.proposal.service.image;

import com.weshare.server.exchange.proposal.entity.ExchangeProposalPost;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPostImage;

public interface ExchangeProposalPostImageService {
    ExchangeProposalPostImage saveImageKey(String key, ExchangeProposalPost exchangeProposalPost);
}
