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
    ALREADY_CLOSED_EXCHANGE_POST(HttpStatus.BAD_REQUEST,"ALREADY_CLOSED_EXCHANGE_POST-401","이미 교환을 완료한 게시글로 더이상 교환을 제안할 수 없습니다."),
    ALREADY_PROPOSED_CANDIDATE_TO_THIS_EXCHANGE_POST(HttpStatus.BAD_REQUEST,"ALREADY_PROPOSED_CANDIDATE_TO_THIS_EXCHANGE_POST-401","이미 해당 게시물에 교환이 제안된 상품입니다."),
    NOT_A_CANDIDATE_POST_OWNER(HttpStatus.BAD_REQUEST,"NOT_A_CANDIDATE_POST_OWNER-401", "해당 물품교환 후보품의 등록자와 현재 서비스 요청자가 일치하지 않습니다."),
    CANNOT_PROPOSE_YOURSELF(HttpStatus.BAD_REQUEST,"CANNOT_PROPOSE_YOURSELF-401","자기 자신이 작성한 제시글에 교환을 제안할 수 없습니다.");

    ;

    private final HttpStatus errorType;
    private final String errorCode;
    private final String message;
}
