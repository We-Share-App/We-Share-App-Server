package com.weshare.server.user.oauthJwt.dto;

import com.weshare.server.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UserRole userRole;
    private String name;
    private String username;
}