package com.weshare.server.user.chat.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "private_user_chat_room_participant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PrivateUserChatParticipant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "private_user_chat_room_id")
    private PrivateUserChatRoom privateUserChatRoom;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked;

    @Column(name = "last_read_at")
    private LocalDateTime lastReadAt;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "left_at", nullable = false)
    private LocalDateTime leftAt;
}
