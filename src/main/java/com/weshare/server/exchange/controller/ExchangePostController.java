package com.weshare.server.exchange.controller;


import com.weshare.server.exchange.candidate.dto.ExchangeCandidatePostDto;
import com.weshare.server.exchange.dto.*;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.exception.post.ExchangePostExceptions;
import com.weshare.server.exchange.candidate.service.ExchangeCandidatePostAggregateService;
import com.weshare.server.exchange.service.ExchangePostAggregateService;
import com.weshare.server.exchange.service.view.ExchangePostViewService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges")
public class ExchangePostController {
    private final ExchangePostAggregateService exchangePostAggregateService;
    private final ExchangeCandidatePostAggregateService exchangeCandidatePostAggregateService;
    private final ExchangePostViewService exchangePostViewService;

    @Operation(
            summary = "공개 물품 교환 게시글 등록 API",
            description = "post로 게시글 정보를 images로 이미지 데이터를 받아 공개 물품 교환 게시글을 등록하는 API"
    )
    @PostMapping(value = "/posts",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExchangePostCreateResponse> createExchangePost(@RequestPart("post") @Valid ExchangePostCreateRequest request, @RequestPart("images")List<MultipartFile> images, @AuthenticationPrincipal CustomOAuth2User principal) {
        ExchangePostCreateResponse response = exchangePostAggregateService.createPostWithImagesAndCategories(request, images, principal);
        return ResponseEntity.ok(response);

    }

    @Operation(
            summary = "공개 물품교환 게시글 전체 조회 API [페이징 제공]",
            description = "쿼리 스트링을 들어온 조건에 따라 공개 물품교환 게시글을 페이징하여 제공하는 API로 두번째 페이지 부턴 lastPostId로 받은 포스트 id 이후의 게시글을 제공함"
    )
    @GetMapping
    public ResponseEntity<ExchangePostListResponse> getExchangePosts(@RequestParam Long locationId,
                                                                     @RequestParam(required = false) Long categoryId,
                                                                     @RequestParam(required = false) String itemCondition,
                                                                     @RequestParam(required = false) Long lastPostId,
                                                                     @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                                     @RequestParam(defaultValue = "10") Integer size,
                                                                     @AuthenticationPrincipal CustomOAuth2User principal){

        ExchangePostFilterDto request = ExchangePostFilterDto.builder()
                .locationId(locationId)
                .categoryId(categoryId)
                .itemCondition(itemCondition)
                .lastPostId(lastPostId)
                .sortDirection(sortDirection)
                .page(0)
                .size(size)
                .build();


        List<ExchangePostDto> exchangePostDtoList = exchangePostAggregateService.getPostsWithImage(request,principal);
        Integer totalPostCount = exchangePostDtoList.size();

        // 조건에 맞는 물품교환 게시글이 없는 경우
        if (exchangePostDtoList.isEmpty()) {
            return ResponseEntity.ok(new ExchangePostListResponse(0,List.of(), null));
        }

        // 조건에 맞는 물품교환 게시글이 존재하는 경우
        // 마지막 응답객체의 ID
        Optional<Long> lastIdOpt = exchangePostDtoList.stream().map(ExchangePostDto::getId).reduce((first, second) -> second);
        Long lastId = lastIdOpt.orElseThrow(()-> new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST_ID));
        ExchangePostListResponse exchangePostListResponse = new ExchangePostListResponse(totalPostCount,exchangePostDtoList,lastId);
        return ResponseEntity.ok(exchangePostListResponse);

    }

    @Operation(
            summary = "공개 물품교환 게시글 단일 조회 API [공개 물품교환 게시글 + 요청된 교환 후보 게시글들]",
            description = "공개 물품교환 게시글을 exchangePostId를 기준으로 찾아서, 해당 게시글과 해당 게시글에 달린 물품교환 후보 게시글을 제공함. 교환이 완료된 게시글은 응답시 postStatus 필드값이 CLOSED 임"
    )
    @GetMapping("/{exchangePostId}")
    public ResponseEntity<?> getOneExchangePost(@PathVariable Long exchangePostId,@AuthenticationPrincipal CustomOAuth2User principal){
        //공개 물품교환 게시글 조회
        ExchangePostDto exchangePostDto = exchangePostAggregateService.getOnePostWithImage(exchangePostId,principal);
        // 물품교환 제안 게시글 조회
        List<ExchangeCandidatePostDto> exchangeCandidatePostDtoList = exchangeCandidatePostAggregateService.getAllCandidateList(exchangePostId);
        //응답생성
        ExchangePostResponse exchangePostResponse = new ExchangePostResponse(exchangePostDto, exchangeCandidatePostDtoList.size(), exchangeCandidatePostDtoList);
        return ResponseEntity.ok(exchangePostResponse);
    }

}
