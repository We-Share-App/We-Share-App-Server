package com.weshare.server.exchange.candidate.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.weshare.server.exchange.candidate.exception.ExchangeCandidatePostException;
import com.weshare.server.exchange.candidate.exception.ExchangeCandidatePostExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ExchangeCandidateStatus {
    AVAILABLE("교환 가능"),
    TRADED("교환 완료");
    private final String description;

    @JsonCreator
    public static ExchangeCandidateStatus stringToEnum(String value) {
        return Arrays.stream(ExchangeCandidateStatus.values())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new ExchangeCandidatePostException(ExchangeCandidatePostExceptions.EXCHANGE_CANDIDATE_STATUS_STRING_TO_ENUM_ERROR));
    }
}
