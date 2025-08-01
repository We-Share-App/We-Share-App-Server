package com.weshare.server.exchange.dto;

import lombok.*;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class ExchangePostFilterDto {
    private Long locationId;
    private Long categoryId;
    private String itemCondition;
    private Long lastPostId;
    private Sort.Direction sortDirection;
    private Integer page;
    private Integer size;

    @Builder
    public ExchangePostFilterDto(Long locationId, Long categoryId, String itemCondition, Long lastPostId, Sort.Direction sortDirection, Integer page, Integer size){
        this.locationId = locationId;
        this.categoryId = categoryId;
        this.itemCondition = itemCondition;
        this.lastPostId = lastPostId;
        this.sortDirection = sortDirection;
        this.page = page;
        this.size = size;
    }
}
