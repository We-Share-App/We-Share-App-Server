package com.weshare.server.groupbuy.chat.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_buy_group_chat_participant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupBuyGroupChatParticipant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_buy_post_id", nullable = false)
    private GroupBuyPost groupBuyPost;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "last_read_at")
    private LocalDateTime lastReadAt;

    @Column(name = "left_at", nullable = false)
    private LocalDateTime leftAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
}
