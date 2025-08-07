package com.weshare.server.groupbuy.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class GroupBuyPostFilterDto {
    private Long locationId;
    private Integer priceLowLimit;
    private Integer priceHighLimit;
    private Integer amount;
    private Long categoryId;
    private Long lastPostId;
    private Sort.Direction sortDirection;
    private Integer page;
    private Integer size;

    @Builder
    public GroupBuyPostFilterDto(Long locationId, Integer priceLowLimit, Integer priceHighLimit, Integer amount, Long categoryId, Long lastPostId, Sort.Direction sortDirection, Integer page, Integer size) {
        this.locationId = locationId;
        this.priceLowLimit = priceLowLimit;
        this.priceHighLimit = priceHighLimit;
        this.amount = amount;
        this.categoryId = categoryId;
        this.lastPostId = lastPostId;
        this.sortDirection = sortDirection;
        this.page = page;
        this.size = size;
    }
}
