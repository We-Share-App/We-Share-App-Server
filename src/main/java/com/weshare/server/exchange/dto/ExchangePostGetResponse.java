package com.weshare.server.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExchangePostGetResponse {
    private List<ExchangePostDto> exchangePostDtoList;
    private Long lastPostId;
}
