package com.weshare.server.exchange.controller;


import com.weshare.server.exchange.dto.*;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.exception.post.ExchangePostExceptions;
import com.weshare.server.exchange.proposal.service.ExchangeProposalPostAggregateService;
import com.weshare.server.exchange.service.ExchangePostAggregateService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
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
    private final ExchangeProposalPostAggregateService exchangeProposalPostAggregateService;

    @PostMapping(value = "/posts",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExchangePostCreateResponse> createExchangePost(@RequestPart("post") @Valid ExchangePostCreateRequest request, @RequestPart("images")List<MultipartFile> images, @AuthenticationPrincipal CustomOAuth2User principal) {
        ExchangePostCreateResponse response = exchangePostAggregateService.createPostWithImagesAndCategories(request, images, principal);
        return ResponseEntity.ok(response);

    }

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

    @GetMapping("/{exchangePostId}")
    public ResponseEntity<?> getOneExchangePost(@PathVariable Long exchangePostId,@AuthenticationPrincipal CustomOAuth2User principal){
        //공개 물품교환 게시글 조회
        ExchangePostDto exchangePostDto = exchangePostAggregateService.getOnePostWithImage(exchangePostId,principal);
        // 물품교환 제안 게시글 조회
        List<ExchangeProposalPostDto> exchangeProposalPostDtoList = exchangeProposalPostAggregateService.getAllProposalList(exchangePostId);
        //응답생성
        ExchangePostResponse exchangePostResponse = new ExchangePostResponse(exchangePostDto,exchangeProposalPostDtoList.size(),exchangeProposalPostDtoList);
        return ResponseEntity.ok(exchangePostResponse);
    }

}
