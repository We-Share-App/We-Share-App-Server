package com.weshare.server.groupbuy.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "group_buy_post_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GroupBuyPostImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_buy_post_image_key", nullable = false)
    private String groupBuyPostImageKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_buy_post_id", nullable = false)
    private GroupBuyPost groupBuyPost;

    @Builder
    public GroupBuyPostImage(String groupBuyPostImageKey, GroupBuyPost groupBuyPost) {
        this.groupBuyPostImageKey = groupBuyPostImageKey;
        this.groupBuyPost = groupBuyPost;
    }
}
