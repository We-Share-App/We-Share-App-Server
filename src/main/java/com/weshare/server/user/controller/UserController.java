package com.weshare.server.user.controller;

import com.weshare.server.user.category.dto.UserCategoryRequest;
import com.weshare.server.user.category.dto.UserCategoryResponse;
import com.weshare.server.user.category.service.UserCategoryService;
import com.weshare.server.user.dto.NicknameAvailabilityResponse;
import com.weshare.server.user.dto.NicknameUpdateResponse;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserCategoryService userCategoryService;

    @Operation(
            summary = "닉네임이 사용가능 여부 응답 API",
            description = "해당 닉네임이 사용가능한 경우 true, 사용 불가능한 경우 false를 리턴함"
    )
    @PostMapping("/nicknames/available")
    public ResponseEntity<NicknameAvailabilityResponse> isAvailableNicknameCheck(@RequestParam(name = "nickname") String nickname){
        Boolean isExist = userService.isAlreadyExistNickname(nickname);
        return ResponseEntity.ok(new NicknameAvailabilityResponse(!isExist));
    }

    @Operation(
            summary = "닉네임 등록 API",
            description = "닉네임 등록 당시 아직 등록되지 않은 사용가능한 닉네임이라면, 닉네임 업데이트를 진행함, 그렇지 않다면 예외 메시지를 리턴함"
    )

    @PostMapping("/nicknames")
    public ResponseEntity<NicknameUpdateResponse> updateNickname(@RequestParam("nickname") String nickname, @RequestHeader("access") String accessToken){
        User user = userService.findUserByAccessToken(accessToken);
        Boolean isExist = userService.isAlreadyExistNickname(nickname);

        // 등록 불가능한 닉네임인 경우
        if(isExist){
            throw new UserException(UserExceptions.NOT_AVAILABLE_NICKNAME);
        }

        // 등록 가능한 닉네임인 경우
        userService.updateNickname(user,nickname);
        return ResponseEntity.ok(new NicknameUpdateResponse(true,nickname));
    }
}
