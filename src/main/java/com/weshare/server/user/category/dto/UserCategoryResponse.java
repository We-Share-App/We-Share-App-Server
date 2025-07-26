package com.weshare.server.user.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserCategoryResponse {
    private Boolean isSuccess;
    private List<Long> userCategoryIdList;

    @Builder
    public UserCategoryResponse(Boolean isSuccess, List<Long>userCategoryIdList){
        this.isSuccess = isSuccess;
        this.userCategoryIdList = userCategoryIdList;
    }
}
