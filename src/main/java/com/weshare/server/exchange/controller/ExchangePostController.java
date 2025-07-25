package com.weshare.server.exchange.controller;

import com.weshare.server.aws.s3.service.S3Service;
import com.weshare.server.exchange.dto.ExchangePostRequest;
import com.weshare.server.exchange.dto.ExchangePostResponse;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostCategory;
import com.weshare.server.exchange.service.ExchangePostCategoryService;
import com.weshare.server.exchange.service.ExchangePostImageService;
import com.weshare.server.exchange.service.ExchangePostService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.service.UserService;
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
@RequestMapping("/exchanges/posts")
public class ExchangePostController {
    private final ExchangePostService exchangePostService;
    private final ExchangePostCategoryService exchangePostCategoryService;
    private final ExchangePostImageService exchangePostImageService;
    private final S3Service s3Service;
    private static  final String directory = "exchange";

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExchangePostResponse> createExchangePost(@RequestPart("post")ExchangePostRequest request, @RequestPart("images")List<MultipartFile> images, @AuthenticationPrincipal CustomOAuth2User principal) {
        // 게시글 업로드
        ExchangePost exchangePost = exchangePostService.createExchangePost(request, principal);
        // 게시글 교환희망 카테고리 업로드
        for(Long categoryId : request.getItemCategoryIdList()){
            exchangePostCategoryService.createExchangePostCategory(categoryId,exchangePost);
        }
        //게시글 이미지 업로드 & 이미지 키 DB 저장 (exchange_post_image 테이블)
        for(MultipartFile img : images){
            String key = s3Service.uploadImage(directory,img);
            exchangePostImageService.saveImageKey(key,exchangePost);
        }

        return ResponseEntity.ok(new ExchangePostResponse(true, exchangePost.getId()));

    }
}
