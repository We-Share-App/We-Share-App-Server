package com.weshare.server.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangePostCreateResponse {
    private Boolean isSuccess;
    private Long exchangePostId;
}
