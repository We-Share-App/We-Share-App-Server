package com.weshare.server.user.chat.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "private_user_chat_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PrivateUserChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
