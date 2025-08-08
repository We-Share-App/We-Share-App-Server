package com.weshare.server.groupbuy.controller;

import com.weshare.server.groupbuy.dto.GroupBuyParticipantRequest;
import com.weshare.server.groupbuy.dto.GroupBuyParticipantResponse;
import com.weshare.server.groupbuy.entity.GroupBuyParticipant;
import com.weshare.server.groupbuy.service.GroupBuyAggregateService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groupbuys/participants")
public class GroupBuyParticipantController {
    private final GroupBuyAggregateService groupBuyAggregateService;

    @Operation(
            summary = "공동구매 참여요청 API",
            description = "대상 게시글 Id인 GroupBuyPostId와 참여수량인 amount를 기준으로 공동구매 참여를 요청함"
    )
    @PostMapping
    public ResponseEntity<GroupBuyParticipantResponse>doGroupBuyParticipant(@RequestBody @Valid GroupBuyParticipantRequest request, @AuthenticationPrincipal CustomOAuth2User principal){
        GroupBuyParticipant groupBuyParticipant = groupBuyAggregateService.doGroupBuyParticipant(request,principal);
        GroupBuyParticipantResponse response = new GroupBuyParticipantResponse(true,groupBuyParticipant.getId());
        return ResponseEntity.ok(response);
    }
}
