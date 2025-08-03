package com.weshare.server.exchange.candidate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExchangeCandidateStatus {
    AVAILABLE("교환 가능"),
    TRADED("교환 완료");
    private final String description;
}
