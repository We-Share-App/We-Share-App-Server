package com.weshare.server.exchange.dto;

import com.weshare.server.category.entity.Category;
import com.weshare.server.exchange.ItemCondition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangePostRequest {

    @NotBlank
    @Size(max = 50)
    private String itemName;

    @NotNull
    @Size(max = 250)
    private List<Long> itemCategoryIdList;

    @NotNull
    private ItemCondition itemCondition;

    @NotNull
    private LocalDateTime expirationDateTime;

    @NotNull
    private Long locationId;


}
