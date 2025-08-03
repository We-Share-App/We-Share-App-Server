package com.weshare.server.exchange.proposal.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExchangeProposalExceptions {
    NOT_EXIST_EXCHANGE_PROPOSAL_POST(HttpStatus.BAD_REQUEST,"NOT_EXIST_EXCHANGE_PROPOSAL_POST_ID-401","존재하지 않는 물품교환 제안 게시글 입니다."),
    NOT_AVAILABLE_EXCHANGE_PROPOSAL_POST(HttpStatus.BAD_REQUEST,"NOT_AVAILABLE_EXCHANGE_PROPOSAL_POST-401", "교환 제안이 불가능한 게시글 입니다."),
    ALREADY_CLOSED_EXCHANGE_PROPOSAL_POST(HttpStatus.BAD_REQUEST,"ALREADY_CLOSED_EXCHANGE_PROPOSAL_POST-401","이미 교환을 완료한 게시글로 더이상 교환을 제안할 수 없습니다."),
    CANNOT_PROPOSE_YOURSELF(HttpStatus.BAD_REQUEST,"CANNOT_PROPOSE_YOURSELF-401","자기 자신이 작성한 제시글에 교환을 제안할 수 없습니다.");

    ;

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
