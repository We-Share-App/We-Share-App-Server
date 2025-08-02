package com.weshare.server.exchange.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ExchangeProposalPostDto {
    private Long id;
    private  String itemName;
    private String itemDescription;
    private String itemCondition;
    private String categoryName;
    private List<String> imageUrlList;

    @Builder
    public ExchangeProposalPostDto(Long id,String itemName, String itemDescription, String itemCondition, String categoryName, List<String> imageUrlList) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCondition = itemCondition;
        this.categoryName = categoryName;
        this.imageUrlList = imageUrlList;
    }
}
