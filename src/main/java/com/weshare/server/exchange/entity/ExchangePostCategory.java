package com.weshare.server.exchange.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exchange_post_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExchangePostCategory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
