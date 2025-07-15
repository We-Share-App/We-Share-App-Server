package com.weshare.server.groupbuy.chat.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_buy_group_chat_room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupBuyGroupChatRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_buy_post_id", nullable = false)
    private GroupBuyPost groupBuyPost;
}
