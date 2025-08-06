package com.weshare.server.groupbuy.controller;

import com.weshare.server.groupbuy.dto.GroupBuyPostCreateRequest;
import com.weshare.server.groupbuy.dto.GroupBuyPostCreateResponse;
import com.weshare.server.groupbuy.service.GroupBuyAggregateService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groupbuys")
public class GroupBuyPostController {
    private final GroupBuyAggregateService groupBuyAggregateService;

    @Operation(
            summary = "공동구매 게시글 등록 API",
            description = "post로 게시글 정보를 images로 이미지 데이터를 받아 공동구매 게시글을 등록하는 API"
    )
    @PostMapping(value = "/posts",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GroupBuyPostCreateResponse>createGroupBuyPost(@RequestPart("post") @Valid GroupBuyPostCreateRequest groupBuyPostCreateRequest, @RequestPart("images") List<MultipartFile> images, @AuthenticationPrincipal CustomOAuth2User principal ){
        GroupBuyPostCreateResponse groupBuyPostCreateResponse= groupBuyAggregateService.createGroupBuyPost(groupBuyPostCreateRequest,images,principal);
        return ResponseEntity.ok(groupBuyPostCreateResponse);
    }
}
