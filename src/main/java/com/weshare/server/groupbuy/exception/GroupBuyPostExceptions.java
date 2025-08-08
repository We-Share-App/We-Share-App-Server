package com.weshare.server.groupbuy.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GroupBuyPostExceptions {
    EXPIRATION_DATE_EARLY_ERROR(HttpStatus.BAD_REQUEST,"EXPIRATION_DATE_ERROR-401","모집 마감일이 금일보다 빠를수는 없습니다."),
    NOT_EXIST_GROUP_POST(HttpStatus.BAD_REQUEST,"NOT_EXIST_GROUP_POST-401","존재하지 않는 공동구매 게시글 입니다."),
    AMOUNT_BAD_REQUEST(HttpStatus.BAD_REQUEST,"AMOUNT_BAD_REQUEST-401", "유효하지 않은 물품수량 범위 입니다."),
    PRICE_BAD_REQUEST(HttpStatus.BAD_REQUEST,"PRICE_BAD_REQUEST-401","유효하지 않은 가격범위 입니다."),
    ALREADY_GROUP_BUY_PARTICIPANT_USER(HttpStatus.BAD_REQUEST,"ALREADY_GROUP_BUY_PARTICIPANT_USER-401","이미 해당 공동구매에 참여한 사용자 입니다"),
    INSUFFICIENT_REMAIN_QUANTITY(HttpStatus.BAD_REQUEST,"INSUFFICIENT_REMAIN_QUANTITY-401","보유 재고량이 부족하여 해당 주문을 접수할 수 없습니다."),
    CANNOT_PARTICIPATE_YOUR_POST_YOU_WROTE(HttpStatus.BAD_REQUEST,"CANNOT_PARTICIPATE_YOUR_POST_YOU_WROTE-401","자신이 작성한 게시글에 참여 할수 없습니다."),
    NOT_EXIST_GROUP_BUY_POST_LIKE(HttpStatus.BAD_REQUEST,"NOT_EXIST_GROUP_BUY_POST_LIKE-401","좋아요 내역을 찾을수 없습니다."),
    ;
    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
