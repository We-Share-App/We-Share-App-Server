package com.weshare.server.exchange.candidate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ExchangeCandidatePostDto {
    private Long id;
    private  String itemName;
    private String itemDescription;
    private String itemCondition;
    private String categoryName;
    private List<String> imageUrlList;
    private String writerNickname;

    @Builder
    public ExchangeCandidatePostDto(Long id, String itemName, String itemDescription, String itemCondition, String categoryName, List<String> imageUrlList, String writerNickname) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCondition = itemCondition;
        this.categoryName = categoryName;
        this.imageUrlList = imageUrlList;
        this.writerNickname = writerNickname;
    }
}
