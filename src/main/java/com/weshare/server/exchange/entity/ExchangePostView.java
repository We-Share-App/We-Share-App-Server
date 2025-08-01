package com.weshare.server.exchange.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exchange_post_view", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "exchange_post_id" }))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExchangePostView extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_post_id", nullable = false)
    private ExchangePost exchangePost;

    @Builder
    public ExchangePostView(User user, ExchangePost exchangePost) {
        this.user = user;
        this.exchangePost = exchangePost;
    }
}
