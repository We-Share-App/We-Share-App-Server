package com.weshare.server.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus {

    PENDING("결제 대기"),
    PAID("결제 완료"),
    CANCELED("결제 취소");
    private final String status;
}
