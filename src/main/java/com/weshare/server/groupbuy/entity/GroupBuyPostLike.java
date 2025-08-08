package com.weshare.server.groupbuy.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.common.entity.LikeStatus;
import com.weshare.server.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_buy_post_like", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "group_buy_post_id" }))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupBuyPostLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_buy_post_id", nullable = false)
    private GroupBuyPost groupBuyPost;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    @Builder
    public GroupBuyPostLike(User user, GroupBuyPost groupBuyPost, LikeStatus likeStatus) {
        this.user = user;
        this.groupBuyPost = groupBuyPost;
        this.likeStatus = likeStatus;
    }

    public GroupBuyPostLike updateLikeStatus(LikeStatus likeStatus){
        this.likeStatus = likeStatus;
        return this;
    }
}
