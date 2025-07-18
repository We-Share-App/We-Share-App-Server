package com.weshare.server.user.chat.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "private_user_chat_message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PrivateUserChatMessage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "private_user_chat_room_id", nullable = false)
    private PrivateUserChatRoom privateUserChatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_user_id", nullable = false)
    private User receiver;
}
