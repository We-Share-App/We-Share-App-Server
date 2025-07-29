package com.weshare.server.exchange.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangePostCreateRequest {

    @NotBlank
    @Size(max = 50)
    private String itemName;

    @NotNull
    private List<Long> itemCategoryIdList;

    @NotNull
    private String itemCondition;

    @NotBlank
    @Size(max = 250)
    private String itemDescription;

    @NotNull
    private Integer activeDuration;

    @NotNull
    private Long locationId;

}
