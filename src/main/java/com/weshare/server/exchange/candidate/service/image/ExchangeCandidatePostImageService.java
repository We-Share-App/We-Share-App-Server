package com.weshare.server.exchange.candidate.service.image;

import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePostImage;

import java.util.List;

public interface ExchangeCandidatePostImageService {
    ExchangeCandidatePostImage saveImageKey(String key, ExchangeCandidatePost exchangeCandidatePost);
    List<String> getImageKey(ExchangeCandidatePost exchangeCandidatePost);
}
