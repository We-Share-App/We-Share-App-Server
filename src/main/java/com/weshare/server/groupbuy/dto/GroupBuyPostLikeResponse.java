package com.weshare.server.groupbuy.dto;

import com.weshare.server.common.entity.LikeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GroupBuyPostLikeResponse {
    private Boolean isSuccess;
    private Long groupBuyPostLikeId;
    private LikeStatus likeStatus;

    @Builder
    public GroupBuyPostLikeResponse(Boolean isSuccess, Long groupBuyPostLikeId, LikeStatus likeStatus) {
        this.isSuccess = isSuccess;
        this.groupBuyPostLikeId = groupBuyPostLikeId;
        this.likeStatus = likeStatus;
    }
}
