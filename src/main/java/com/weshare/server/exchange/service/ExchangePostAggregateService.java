package com.weshare.server.exchange.service;

import com.weshare.server.aws.s3.service.S3Service;
import com.weshare.server.exchange.dto.*;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPost;
import com.weshare.server.exchange.proposal.service.post.ExchangeProposalPostService;
import com.weshare.server.exchange.service.category.ExchangePostCategoryService;
import com.weshare.server.exchange.service.image.ExchangePostImageService;
import com.weshare.server.exchange.service.post.ExchangePostService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ExchangePostAggregateService {
    private final ExchangePostService exchangePostService;
    private final ExchangePostCategoryService exchangePostCategoryService;
    private final ExchangePostImageService exchangePostImageService;
    private final S3Service s3Service;
    private static final String directory = "exchange";
    @Transactional
    public ExchangePostCreateResponse createPostWithImagesAndCategories(ExchangePostCreateRequest request, List<MultipartFile> images, CustomOAuth2User principal) {
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

        return new ExchangePostCreateResponse(true, post.getId());
    }

    @Transactional
    public List<ExchangePostDto> getPostsWithImage(ExchangePostFilterDto request, CustomOAuth2User principal){

        // 포스트 엔티티 필터링 전체 조회
        List<ExchangePost> exchangePostList = exchangePostService.getFilteredExchangePost(request);

        //게시물 데이터 리스트 객체
        List<ExchangePostDto> exchangePostDtoList = new ArrayList<>();

        // 각각의 포스트 엔티티에 대하여 이미지키를 찾아와 presigned URL 획득하기
        for(ExchangePost exchangePost : exchangePostList){
            // 각각의 포스트 엔티티에 대하여 교환 희망 카테고리를 찾아와 문자열 리스트로 저장하기
            List<String> exchangePostCategoryList = exchangePostCategoryService.getExchangePostCategoryNameList(exchangePost);
            // 각각의 포스트 엔티티에 대하여 이미지키를 찾아와 presigned URL 획득하기
            List<String> presignedUrlList = exchangePostImageService.getImageKey(exchangePost).stream().map(s3Service::getPresignedUrl).collect(Collectors.toList());
            // 각각의 포스트 엔티티에 대하여 좋아요 개수를 획득하기
            Long likes = exchangePostService.getLikeCount(exchangePost);
            Boolean isUserLiked = exchangePostService.isUserLikedPost(principal,exchangePost);

            ExchangePostDto exchangePostDto = ExchangePostDto.builder()
                    .id(exchangePost.getId())
                    .itemName(exchangePost.getItemName())
                    .itemCondition(exchangePost.getItemCondition().getDescription())
                    .categoryName(exchangePostCategoryList)
                    .createdAt(exchangePost.getCreatedAt())
                    .likes(likes)
                    .imageUrlList(presignedUrlList)
                    .isUserLiked(isUserLiked)
                    .build();
            exchangePostDtoList.add(exchangePostDto);
        }

        return exchangePostDtoList;

    }

    @Transactional
    public ExchangePostDto getOnePostWithImage(Long exchangePostId, CustomOAuth2User principal){
        ExchangePost exchangePost = exchangePostService.findExchangePost(exchangePostId);
        // 포스트 엔티티에 대하여 교환 희망 카테고리를 찾아와 문자열 리스트로 저장하기
        List<String> exchangePostCategoryList = exchangePostCategoryService.getExchangePostCategoryNameList(exchangePost);
        // 포스트 엔티티에 대하여 이미지키를 찾아와 presigned URL 획득하기
        List<String> presignedUrlList = exchangePostImageService.getImageKey(exchangePost).stream().map(s3Service::getPresignedUrl).collect(Collectors.toList());
        //포스트 엔티티에 대하여 좋아요 개수를 획득하기
        Long likes = exchangePostService.getLikeCount(exchangePost);
        Boolean isUserLiked = exchangePostService.isUserLikedPost(principal,exchangePost);

        ExchangePostDto exchangePostDto = ExchangePostDto.builder()
                .id(exchangePost.getId())
                .itemName(exchangePost.getItemName())
                .itemCondition(exchangePost.getItemCondition().getDescription())
                .categoryName(exchangePostCategoryList)
                .createdAt(exchangePost.getCreatedAt())
                .likes(likes)
                .imageUrlList(presignedUrlList)
                .isUserLiked(isUserLiked)
                .build();

        return exchangePostDto;
    }

}
