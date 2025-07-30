package com.weshare.server.user.location.controller;

import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.location.dto.UserLocationRegistrationRequest;
import com.weshare.server.user.location.dto.UserLocationRegistrationResponse;
import com.weshare.server.user.location.entity.UserLocation;
import com.weshare.server.user.location.service.UserLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/locations")
public class UserLocationController {
    private final UserLocationService userLocationService;

    @PostMapping
    public ResponseEntity<UserLocationRegistrationResponse> userLocationRegister(@RequestBody UserLocationRegistrationRequest request, @AuthenticationPrincipal CustomOAuth2User principal){
        UserLocation userLocation = userLocationService.createUserLocation(request,principal);
        UserLocationRegistrationResponse response = new UserLocationRegistrationResponse(true, userLocation.getId());
        return ResponseEntity.ok(response);
    }
}
