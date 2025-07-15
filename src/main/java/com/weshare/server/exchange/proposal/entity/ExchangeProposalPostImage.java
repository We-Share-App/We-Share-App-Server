package com.weshare.server.exchange.proposal.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exchange_proposal_post_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExchangeProposalPostImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exchange_proposal_post_image_key", nullable = false)
    private String exchangeProposalPostImageKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_proposal_post_id", nullable = false)
    private ExchangeProposalPost exchangeProposalPost;

}