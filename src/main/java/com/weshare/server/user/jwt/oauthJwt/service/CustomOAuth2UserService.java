package com.weshare.server.user.jwt.oauthJwt.service;

import com.weshare.server.user.entity.User;
import com.weshare.server.user.entity.UserRole;
import com.weshare.server.user.jwt.dto.UserDTO;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.jwt.oauthJwt.dto.GoogleResponse;
import com.weshare.server.user.jwt.oauthJwt.dto.NaverResponse;
import com.weshare.server.user.jwt.oauthJwt.dto.OAuth2Response;
import com.weshare.server.user.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        }
        else {

            return null;
        }
        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        User existData = userRepository.findByUsername(username);

        // 기존에 DB에 저장된 회원이 아닌경우 -> DB 저장
        if (existData == null) {

            User user = new User(username,oAuth2Response.getName(),oAuth2Response.getEmail(),UserRole.ROLE_USER);
            userRepository.save(user);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(username);
            userDTO.setName(oAuth2Response.getName());
            userDTO.setUserRole(UserRole.ROLE_USER);

            return new CustomOAuth2User(userDTO); // OAuth2User 객체 반환
        }
        // 기존에 DB에 저장된 회원인 경우 -> DB 업데이트
        else {
            existData.changeEmail(oAuth2Response.getEmail());
            existData.changeName(oAuth2Response.getName());

            userRepository.save(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(existData.getUsername());
            userDTO.setName(oAuth2Response.getName());
            //userDTO.setRole(existData.getRole());
            userDTO.setUserRole(existData.getUserRole());

            return new CustomOAuth2User(userDTO); // OAuth2User 객체 반환
        }
    }
}
