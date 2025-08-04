package com.weshare.server.exchange.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class ExchangePostDto {
    private Long exchangePostId;
    private String itemName;
    private String itemCondition;
    private String postStatus;
    private List<String> categoryName;
    private LocalDateTime createdAt;
    private Long likes;
    private List<String> imageUrlList;
    private Boolean isUserLiked;
    private Long viewCount;
    private Boolean isYours;

    @Builder
    public ExchangePostDto(Long exchangePostId, String itemName, String itemCondition, String postStatus, List<String> categoryName, LocalDateTime createdAt, Long likes, List<String> imageUrlList, Boolean isUserLiked, Long viewCount, Boolean isYours) {
        this.exchangePostId = exchangePostId;
        this.itemName = itemName;
        this.itemCondition = itemCondition;
        this.postStatus = postStatus;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.likes = likes;
        this.imageUrlList = imageUrlList;
        this.isUserLiked = isUserLiked;
        this.viewCount = viewCount;
        this.isYours = isYours;
    }

    public Long updateViewCount(Long viewCount){
        this.viewCount = viewCount;
        return viewCount;
    }
}
