package com.weshare.server.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus {
    POST_OWNER("공동구매 진행자 예외"),
    PENDING("결제 대기"),
    PAID("결제 완료"),
    CANCELED("결제 취소");
    private final String status;
}
