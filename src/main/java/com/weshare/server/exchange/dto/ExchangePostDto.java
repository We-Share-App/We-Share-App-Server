package com.weshare.server.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class ExchangePostDto {
    private Long id;
    private String itemName;
    private String itemCondition;
    private List<String> categoryName;
    private LocalDateTime createdAt;
    private Long likes;
    private List<String> imageUrlList;
    private Boolean isUserLiked;

    @Builder
    public ExchangePostDto(Long id, String itemName, String itemCondition, List<String> categoryName, LocalDateTime createdAt, Long likes, List<String> imageUrlList, Boolean isUserLiked) {
        this.id = id;
        this.itemName = itemName;
        this.itemCondition = itemCondition;
        this.categoryName = categoryName;
        this.createdAt = createdAt;
        this.likes = likes;
        this.imageUrlList = imageUrlList;
        this.isUserLiked = isUserLiked;
    }
}
