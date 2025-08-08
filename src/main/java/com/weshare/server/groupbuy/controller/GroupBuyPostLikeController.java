package com.weshare.server.groupbuy.controller;

import com.weshare.server.groupbuy.dto.GroupBuyPostLikeResponse;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.entity.GroupBuyPostLike;
import com.weshare.server.groupbuy.service.like.GroupBuyPostLikeService;
import com.weshare.server.groupbuy.service.post.GroupBuyPostService;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groupbuys/likes")
public class GroupBuyPostLikeController {
    private final UserService userService;
    private final GroupBuyPostService groupBuyPostService;
    private final GroupBuyPostLikeService groupBuyPostLikeService;

    @Operation(
            summary = "공동구매 게시글 좋아요 등록 API",
            description = "groupBuyPostId를 기준으로 해당 공동구매 게시글에 대해 좋아요를 등록하는 API"
    )
    @PostMapping
    public ResponseEntity<GroupBuyPostLikeResponse>createPostLike(@RequestParam("groupBuyPostId")Long groupBuyPostId, @AuthenticationPrincipal CustomOAuth2User principal){
        User user = userService.findUserByUsername(principal.getUsername());
        GroupBuyPost groupBuyPost = groupBuyPostService.findPostById(groupBuyPostId);
        GroupBuyPostLike groupBuyPostLike = groupBuyPostLikeService.addPostLike(groupBuyPost,user);

        GroupBuyPostLikeResponse response = GroupBuyPostLikeResponse.builder()
                .isSuccess(true)
                .groupBuyPostLikeId(groupBuyPostLike.getId())
                .likeStatus(groupBuyPostLike.getLikeStatus())
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "공동구매 게시글 좋아요 취소  API",
            description = "groupBuyPostId를 기준으로 해당 공동구매 게시글에 대해 좋아요를 취소하는 API"
    )
    @PostMapping("/cancel")
    public ResponseEntity<GroupBuyPostLikeResponse>deletePostLike(@RequestParam("groupBuyPostId")Long groupBuyPostId, @AuthenticationPrincipal CustomOAuth2User principal){
        User user = userService.findUserByUsername(principal.getUsername());
        GroupBuyPost groupBuyPost = groupBuyPostService.findPostById(groupBuyPostId);
        GroupBuyPostLike groupBuyPostLike = groupBuyPostLikeService.deletePostLike(groupBuyPost,user);

        GroupBuyPostLikeResponse response = GroupBuyPostLikeResponse.builder()
                .isSuccess(true)
                .groupBuyPostLikeId(groupBuyPostLike.getId())
                .likeStatus(groupBuyPostLike.getLikeStatus())
                .build();
        return ResponseEntity.ok(response);
    }
}
