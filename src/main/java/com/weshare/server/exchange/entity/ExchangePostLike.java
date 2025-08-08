package com.weshare.server.exchange.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exchange_post_like", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "exchange_post_id" }))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExchangePostLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_post_id", nullable = false)
    private ExchangePost exchangePost;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    @Builder
    public ExchangePostLike(User user, ExchangePost exchangePost, LikeStatus likeStatus) {
        this.user = user;
        this.exchangePost = exchangePost;
        this.likeStatus = likeStatus;
    }

    public ExchangePostLike updateLikeStatus(LikeStatus likeStatus){
        this.likeStatus = likeStatus;
        return this;
    }
}
