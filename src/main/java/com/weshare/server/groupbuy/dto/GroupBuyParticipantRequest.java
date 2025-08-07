package com.weshare.server.groupbuy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupBuyParticipantRequest {
    @NotNull
    private Long groupBuyPostId;
    @NotNull
    private Integer amount;
}
