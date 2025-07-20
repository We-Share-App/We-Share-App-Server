package com.weshare.server.user.service;

import com.weshare.server.user.entity.User;


public interface UserService {
    Boolean isAlreadyExistNickname(String nickname);

    User findUserByAccessToken(String accessToken);

    User updateNickname(User user,String nickname);
}
