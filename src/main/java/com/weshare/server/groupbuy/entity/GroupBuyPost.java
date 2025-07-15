package com.weshare.server.groupbuy.entity;

import com.weshare.server.category.entity.Category;
import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.location.entity.Location;
import com.weshare.server.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_buy_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupBuyPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_description", nullable = false)
    private String itemDescription;

    @Column(name = "item_url", nullable = false)
    private String itemUrl;

    @Max(999) // @vaild 만 제약, DB 제약 미반영
    @Column(name = "item_quantity", nullable = false)
    private Integer itemQuantity;

    @Column(name = "item_price", nullable = false)
    private Integer itemPrice;

    @Column(name = "shipping_fee",nullable = false)
    private Integer shippingFee;

    @Column(name = "recruiting_expiration_date",nullable = false)
    private LocalDateTime recruitingExpirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

}
