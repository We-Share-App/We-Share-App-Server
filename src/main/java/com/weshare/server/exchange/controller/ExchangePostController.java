package com.weshare.server.exchange.controller;


import com.weshare.server.exchange.dto.*;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.exception.post.ExchangePostExceptions;
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

    @PostMapping(value = "/posts",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExchangePostCreateResponse> createExchangePost(@RequestPart("post") @Valid ExchangePostCreateRequest request, @RequestPart("images")List<MultipartFile> images, @AuthenticationPrincipal CustomOAuth2User principal) {
        ExchangePostCreateResponse response = exchangePostAggregateService.createPostWithImagesAndCategories(request, images, principal);
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<ExchangePostGetResponse> getExchangePosts(@RequestParam Long locationId,
                                                                    @RequestParam(required = false) Long categoryId,
                                                                    @RequestParam(required = false) String itemCondition,
                                                                    @RequestParam(required = false) Long lastPostId,
                                                                    @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection,
                                                                    @RequestParam(defaultValue = "10") Integer size,
                                                                    @AuthenticationPrincipal CustomOAuth2User principal){

        ExchangePostFilterRequest request = ExchangePostFilterRequest.builder()
                .locationId(locationId)
                .categoryId(categoryId)
                .itemCondition(itemCondition)
                .lastPostId(lastPostId)
                .sortDirection(sortDirection)
                .page(0)
                .size(size)
                .build();


        List<ExchangePostDto> exchangePostDtoList = exchangePostAggregateService.getPostWithImage(request,principal);

        // 조건에 맞는 물품교환 게시글이 없는 경우
        if (exchangePostDtoList.isEmpty()) {
            return ResponseEntity.ok(new ExchangePostGetResponse(List.of(), null));
        }

        // 조건에 맞는 물품교환 게시글이 존재하는 경우
        // 마지막 응답객체의 ID
        Optional<Long> lastIdOpt = exchangePostDtoList.stream().map(ExchangePostDto::getId).reduce((first, second) -> second);
        Long lastId = lastIdOpt.orElseThrow(()-> new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST_ID));
        ExchangePostGetResponse exchangePostGetResponse = new ExchangePostGetResponse(exchangePostDtoList,lastId);
        return ResponseEntity.ok(exchangePostGetResponse);

    }
}
