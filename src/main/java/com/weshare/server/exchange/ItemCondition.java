package com.weshare.server.exchange;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ItemCondition {
    NEW("새상품"),
    LIKE_NEW("사용감 없음"),
    USED("사용감 있음");
    private final String description;
}
