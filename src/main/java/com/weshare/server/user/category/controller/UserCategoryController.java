package com.weshare.server.user.category.controller;

import com.weshare.server.user.category.dto.UserCategoryRequest;
import com.weshare.server.user.category.dto.UserCategoryResponse;
import com.weshare.server.user.category.service.UserCategoryService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/categories")
public class UserCategoryController {
    private final UserCategoryService userCategoryService;

    @Operation(
            summary = "관심 카테고리 등록 API",
            description = "관심 카테고리 ID를 리스트로 받아서 저장함, 존재하지 않는 카테고리 ID가 입력되는 경우 예외로 응답함"
    )
    @PostMapping()
    public ResponseEntity<UserCategoryResponse> createUserCategory(@RequestBody UserCategoryRequest request, @AuthenticationPrincipal CustomOAuth2User principal){
        List<Long> userCategoryIdList = userCategoryService.createUserCategory(request.getCategoryIdList(),principal);
        UserCategoryResponse response = UserCategoryResponse.builder()
                .isSuccess(true)
                .userCategoryIdList(userCategoryIdList)
                .build();

        return ResponseEntity.ok(response);
    }
}
