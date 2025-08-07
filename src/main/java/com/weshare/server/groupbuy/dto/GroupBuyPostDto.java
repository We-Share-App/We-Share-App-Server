package com.weshare.server.groupbuy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class GroupBuyPostDto {
    private Long groupBuyPostId;
    private String itemName;
    private String itemDescription;
    private String itemUrl;
    private Integer itemPrice;
    private Integer totalQuantity;
    private Integer remainQuantity;
    private List<String> imageUrlList;
    private String categoryName;
    private LocalDateTime expirationDateTime;
    private Long viewCount;
    private Long likes;
    private Integer participantCount;
    private Boolean isUserLiked;
    private Boolean isYours;
    private String userNickname;

    @Builder
    public GroupBuyPostDto(Long groupBuyPostId, String itemName, String itemDescription,String itemUrl,Integer itemPrice, Integer totalQuantity, Integer remainQuantity, List<String> imageUrlList, String categoryName, LocalDateTime expirationDateTime, Long viewCount, Long likes, Integer participantCount, Boolean isUserLiked, Boolean isYours, String userNickname) {
        this.groupBuyPostId = groupBuyPostId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemUrl = itemUrl;
        this.itemPrice = itemPrice;
        this.totalQuantity = totalQuantity;
        this.remainQuantity = remainQuantity;
        this.imageUrlList = imageUrlList;
        this.categoryName = categoryName;
        this.expirationDateTime = expirationDateTime;
        this.viewCount = viewCount;
        this.likes = likes;
        this.participantCount = participantCount;
        this.isUserLiked = isUserLiked;
        this.isYours = isYours;
        this.userNickname = userNickname;
    }
}
