package com.weshare.server.exchange.proposal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProposalStatus {
    PENDING("진행중"),
    ACCEPTED("교환 완료"),
    REJECTED("교환 거절");
    private final String description;
}
