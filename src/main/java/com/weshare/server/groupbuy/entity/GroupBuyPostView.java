package com.weshare.server.groupbuy.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_buy_post_view")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupBuyPostView extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_buy_post_id", nullable = false)
    private GroupBuyPost groupBuyPost;

    @Builder
    public GroupBuyPostView(User user, GroupBuyPost groupBuyPost) {
        this.user = user;
        this.groupBuyPost = groupBuyPost;
    }
}
