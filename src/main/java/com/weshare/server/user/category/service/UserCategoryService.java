package com.weshare.server.user.category.service;

import com.weshare.server.user.category.entity.UserCategory;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;

import java.util.List;

public interface UserCategoryService {
    List<Long> createUserCategory(List<Long> categoryIdList, CustomOAuth2User principal);
}
