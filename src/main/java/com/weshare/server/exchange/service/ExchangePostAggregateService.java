package com.weshare.server.exchange.service;

import com.weshare.server.aws.s3.service.S3Service;
import com.weshare.server.exchange.dto.ExchangePostRequest;
import com.weshare.server.exchange.dto.ExchangePostResponse;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.service.category.ExchangePostCategoryService;
import com.weshare.server.exchange.service.image.ExchangePostImageService;
import com.weshare.server.exchange.service.post.ExchangePostService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ExchangePostAggregateService {
    private final ExchangePostService exchangePostService;
    private final ExchangePostCategoryService exchangePostCategoryService;
    private final ExchangePostImageService exchangePostImageService;
    private final S3Service s3Service;
    private static final String directory = "exchange";
    @Transactional
    public ExchangePostResponse createPostWithImagesAndCategories(ExchangePostRequest request, List<MultipartFile> images, CustomOAuth2User principal) {
        // 게시글 업로드
        ExchangePost post = exchangePostService.createExchangePost(request, principal);

        // 게시글 교환희망 카테고리 업로드
        for (Long categoryId : request.getItemCategoryIdList()) {
            exchangePostCategoryService.createExchangePostCategory(categoryId, post);
        }

        //게시글 이미지 업로드 & 이미지 키 DB 저장 (exchange_post_image 테이블)
        for (MultipartFile img : images) {
            String key = s3Service.uploadImage(directory, img);
            exchangePostImageService.saveImageKey(key, post);
        }

        return new ExchangePostResponse(true, post.getId());
    }
}
