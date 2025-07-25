package com.weshare.server.exchange;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.weshare.server.exchange.exception.item.ItemConditionException;
import com.weshare.server.exchange.exception.item.ItemConditionExceptions;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ItemCondition {
    NEW("새상품"),
    LIKE_NEW("사용감 없음"),
    USED("사용감 있음");
    private final String description;

    @JsonCreator
    public static ItemCondition stringToEnum(String value) {
        return Arrays.stream(ItemCondition.values())
                .filter(e -> e.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new ItemConditionException(ItemConditionExceptions.ITEM_CONDITION_STRING_TO_ENUM_ERROR));
    }
}
