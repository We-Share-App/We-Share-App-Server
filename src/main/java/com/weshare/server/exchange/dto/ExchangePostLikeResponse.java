package com.weshare.server.exchange.dto;

import com.weshare.server.exchange.entity.LikeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ExchangePostLikeResponse {
    private Boolean isSuccess;
    private Long exchangePostLikeId;
    private LikeStatus likeStatus;

    @Builder
    public ExchangePostLikeResponse(Boolean isSuccess, Long exchangePostLikeId, LikeStatus likeStatus) {
        this.isSuccess = isSuccess;
        this.exchangePostLikeId = exchangePostLikeId;
        this.likeStatus = likeStatus;
    }
}
