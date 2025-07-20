package com.weshare.server.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NicknameUpdateResponse {
    Boolean isSuccess;
    String updatedNickname;
}
