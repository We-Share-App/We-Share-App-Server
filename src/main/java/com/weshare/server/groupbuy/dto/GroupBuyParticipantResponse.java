package com.weshare.server.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupBuyParticipantResponse {
    private Boolean isSuccess;
    private Long groupBuyParticipantId;
}
