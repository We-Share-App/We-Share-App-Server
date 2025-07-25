package com.weshare.server.exchange.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.exchange.ItemCondition;
import com.weshare.server.location.entity.Location;
import com.weshare.server.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExchangePost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String itemName;

    @Column(nullable = false)
    private String itemDescription;

    @Column(nullable = false)
    private LocalDateTime recruitingExpirationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemCondition itemCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Builder
    public  ExchangePost(String itemName, String itemDescription, LocalDateTime recruitingExpirationDate, ItemCondition itemCondition ,User user, Location location){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.recruitingExpirationDate = recruitingExpirationDate;
        this.itemCondition = itemCondition;
        this.user = user;
        this.location = location;
    }

}
