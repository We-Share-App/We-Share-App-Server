package com.weshare.server.exchange.candidate.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exchange_candidate_post_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExchangeCandidatePostImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exchange_candidate_post_image_key", nullable = false)
    private String exchangeCandidatePostImageKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_candidate_post_id", nullable = false)
    private ExchangeCandidatePost exchangeCandidatePost;

    @Builder
    public ExchangeCandidatePostImage(String exchangeCandidatePostImageKey, ExchangeCandidatePost exchangeCandidatePost){
        this.exchangeCandidatePostImageKey = exchangeCandidatePostImageKey;
        this.exchangeCandidatePost = exchangeCandidatePost;
    }
}