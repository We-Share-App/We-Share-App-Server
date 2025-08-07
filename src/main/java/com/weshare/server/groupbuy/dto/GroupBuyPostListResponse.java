package com.weshare.server.groupbuy.dto;

import com.weshare.server.groupbuy.entity.GroupBuyPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupBuyPostListResponse {
    private Integer totalGroupBuyPostCount;
    private List<GroupBuyPostDto> groupBuyPostDtoList;
    private Long lastPostId;
}
