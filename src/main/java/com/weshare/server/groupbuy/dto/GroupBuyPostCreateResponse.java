package com.weshare.server.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupBuyPostCreateResponse {
    private Boolean isSuccess;
    private Long groupBuyPostId;
}
