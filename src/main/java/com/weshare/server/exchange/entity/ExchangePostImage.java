package com.weshare.server.exchange.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exchange_post_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExchangePostImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exchange_post_image_key", nullable = false)
    private String exchangePostImageKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_post_id", nullable = false)
    private ExchangePost exchangePost;

    @Builder
    public ExchangePostImage(String exchangePostImageKey, ExchangePost exchangePost){
        this.exchangePostImageKey = exchangePostImageKey;
        this.exchangePost = exchangePost;
    }
}
