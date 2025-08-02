package com.weshare.server.user.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserLocationRegistrationResponse {
    private Boolean isSuccess;
    private Long userLocationId;
    private Long locationId;
}
