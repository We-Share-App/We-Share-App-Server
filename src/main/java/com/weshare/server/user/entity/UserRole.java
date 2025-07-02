package com.weshare.server.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum UserRole {
    ROLE_GUEST("ROLE_GUEST"),
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String roleName;

    public static UserRole stringToUserRole(String roleName) {
        return Arrays.stream(values())
                .filter(r -> r.getRoleName().equals(roleName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown role: " + roleName));
    }
}