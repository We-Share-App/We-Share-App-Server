package com.weshare.server.exchange.controller;

import com.weshare.server.exchange.dto.ExchangePostRequest;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges")
public class ExchangePostController {
    private final UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createExchangePost(@RequestPart("post")ExchangePostRequest request, @RequestPart("images")List<MultipartFile> images, @AuthenticationPrincipal CustomOAuth2User principal) {
        User user = userService.findUserByUsername(principal.getUsername());
        // 업로드 서비스 로직 구현하기


    }
}
