package com.weshare.server.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PostStatus {
    OPEN("교환 가능"),
    CLOSED("교환 불가능");
    private final String description;
}
