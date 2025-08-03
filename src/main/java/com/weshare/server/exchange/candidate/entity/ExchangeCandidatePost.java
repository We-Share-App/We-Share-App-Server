package com.weshare.server.exchange.candidate.entity;

import com.weshare.server.category.entity.Category;
import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.exchange.entity.ItemCondition;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.location.entity.Location;
import com.weshare.server.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exchange_candidate_post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExchangeCandidatePost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name", nullable = false,length = 30)
    private String itemName;

    @Column(name = "item_description", nullable = false)
    private String itemDescription;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemCondition itemCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "exchange_candidate_status",nullable = false)
    private ExchangeCandidateStatus exchangeCandidateStatus;


    @Builder
    public ExchangeCandidatePost(String itemName, String itemDescription, ItemCondition itemCondition, User user, Category category, ExchangeCandidateStatus exchangeCandidateStatus){
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCondition = itemCondition;
        this.user = user;
        this.category = category;
        this.exchangeCandidateStatus = exchangeCandidateStatus;
    }
}
