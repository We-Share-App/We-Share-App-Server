package com.weshare.server.exchange.candidate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeCandidateRequest {
    @NotBlank
    @Size(max = 50)
    private String itemName;

    @NotNull
    private Long itemCategoryId;

    @NotBlank
    private String itemCondition;

    @NotBlank
    @Size(max = 250)
    private String itemDescription;

    @NotNull
    private Long categoryId;
}
