package com.weshare.server.groupbuy.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupBuyPostCreateRequest {
    @NotBlank
    private String itemName;
    @NotNull
    private Long itemCategoryId;
    @NotBlank
    private String itemURL;
    @NotBlank
    private String itemDescription;
    @NotNull
    @JsonProperty("expirationDate")
    private LocalDate expirationDate;
    @NotNull
    private Integer totalQuantity;
    @NotNull
    private Integer writerQuantity;
    @NotNull
    private Integer itemPrice;
    @NotNull
    private Integer shippingFee;
    @NotNull
    private Long locationId;

    public static LocalDateTime dateToDateTimeWithEndOfDayTime(LocalDate localDate){
        return localDate.atTime(23,59,59);
    }
}
