package com.weshare.server.common.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LikeStatus{
    ACTIVATED("좋아요 활성 상태"),
    CANCELED("좋아요 취소 상태");
    private final String description;
}
