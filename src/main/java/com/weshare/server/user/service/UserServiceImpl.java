package com.weshare.server.user.service;

import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.util.JWTUtil;
import com.weshare.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public User findUserByAccessToken(String accessToken) {
        String username = jwtUtil.getUsername(accessToken);
        User user = userRepository.findByUsername(username);

        if(user == null){
            throw new UserException(UserExceptions.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public Boolean isAlreadyExistNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional
    @Override
    public User updateNickname(User user,String nickname) {
        user.updateNickname(nickname);
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UserException(UserExceptions.USER_NOT_FOUND);
        }
        return user;
    }
}
