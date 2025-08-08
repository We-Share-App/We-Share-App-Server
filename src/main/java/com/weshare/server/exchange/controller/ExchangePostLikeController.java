package com.weshare.server.exchange.controller;

import com.weshare.server.exchange.dto.ExchangePostLikeResponse;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostLike;
import com.weshare.server.exchange.service.like.ExchangePostLikeService;
import com.weshare.server.exchange.service.post.ExchangePostService;
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
@RequestMapping("/exchanges/likes")
public class ExchangePostLikeController {
    private final UserService userService;
    private final ExchangePostService exchangePostService;
    private final ExchangePostLikeService exchangePostLikeService;

    @Operation(
            summary = "공개 물품 교환 게시글 좋아요 등록 API",
            description = "exchangePostId를 기준으로 해당 공개 물품 교환 게시글에 대해 좋아요를 등록하는 API"
    )
    @PostMapping
    public ResponseEntity<ExchangePostLikeResponse> createPostLike(@RequestParam("exchangePostId")Long exchangePostId, @AuthenticationPrincipal CustomOAuth2User principal){
        User user = userService.findUserByUsername(principal.getUsername());
        ExchangePost exchangePost = exchangePostService.findExchangePost(exchangePostId);
        ExchangePostLike exchangePostLike = exchangePostLikeService.addPostLike(exchangePost,user);

        ExchangePostLikeResponse response = ExchangePostLikeResponse.builder()
                .isSuccess(true)
                .exchangePostLikeId(exchangePostLike.getId())
                .likeStatus(exchangePostLike.getLikeStatus())
                .build();
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "공개 물품 교환 게시글 좋아요 취소  API",
            description = "exchangePostId를 기준으로 해당 공개 물품 교환 게시글에 대한 좋아요를 취소하는 API"
    )
    @PostMapping("/cancel")
    public ResponseEntity<ExchangePostLikeResponse> deletePostLike(@RequestParam("exchangePostId")Long exchangePostId, @AuthenticationPrincipal CustomOAuth2User principal){
        User user = userService.findUserByUsername(principal.getUsername());
        ExchangePost exchangePost = exchangePostService.findExchangePost(exchangePostId);
        ExchangePostLike exchangePostLike = exchangePostLikeService.deletePostLike(exchangePost,user);

        ExchangePostLikeResponse response = ExchangePostLikeResponse.builder()
                .isSuccess(true)
                .exchangePostLikeId(exchangePostLike.getId())
                .likeStatus(exchangePostLike.getLikeStatus())
                .build();
        return ResponseEntity.ok(response);
    }
}
