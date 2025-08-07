package com.weshare.server.groupbuy.controller;

import com.weshare.server.groupbuy.dto.GroupBuyPostCreateRequest;
import com.weshare.server.groupbuy.dto.GroupBuyPostCreateResponse;
import com.weshare.server.groupbuy.dto.GroupBuyPostDto;
import com.weshare.server.groupbuy.dto.GroupBuyPostResponse;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.service.GroupBuyAggregateService;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groupbuys")
public class GroupBuyPostController {
    private final GroupBuyAggregateService groupBuyAggregateService;
    private final UserService userService;

    @Operation(
            summary = "공동구매 게시글 등록 API",
            description = "post로 게시글 정보를 images로 이미지 데이터를 받아 공동구매 게시글을 등록하는 API"
    )
    @PostMapping(value = "/posts",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GroupBuyPostCreateResponse>createGroupBuyPost(@RequestPart("post") @Valid GroupBuyPostCreateRequest groupBuyPostCreateRequest, @RequestPart("images") List<MultipartFile> images, @AuthenticationPrincipal CustomOAuth2User principal ){
        GroupBuyPostCreateResponse groupBuyPostCreateResponse= groupBuyAggregateService.createGroupBuyPost(groupBuyPostCreateRequest,images,principal);
        return ResponseEntity.ok(groupBuyPostCreateResponse);
    }

    @Operation(
            summary = "공동구매 게시글 단건 조회 API",
            description = "GroupBuyPost의 ID를 기준으로 해당 게시글을 조회하는 API"
    )
    @GetMapping("/{groupBuyPostId}")
    public ResponseEntity<GroupBuyPostResponse> getOneGroupBuyPost(@PathVariable Long groupBuyPostId,@AuthenticationPrincipal CustomOAuth2User principal){
        GroupBuyPostDto groupBuyPostDto = groupBuyAggregateService.getOnePostWithImage(groupBuyPostId,principal);
        GroupBuyPostResponse groupBuyPostResponse = new GroupBuyPostResponse(groupBuyPostDto);
        return  ResponseEntity.ok(groupBuyPostResponse);
    }

}
