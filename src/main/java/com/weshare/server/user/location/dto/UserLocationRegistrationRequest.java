package com.weshare.server.user.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserLocationRegistrationRequest {
    private String stateName;
    private String cityName;
    private String townName;
}
