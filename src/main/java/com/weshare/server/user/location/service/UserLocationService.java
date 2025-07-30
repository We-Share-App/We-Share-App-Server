package com.weshare.server.user.location.service;

import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.location.dto.UserLocationRegistrationRequest;
import com.weshare.server.user.location.entity.UserLocation;

public interface UserLocationService {
    UserLocation createUserLocation(UserLocationRegistrationRequest request, CustomOAuth2User principal);
}
