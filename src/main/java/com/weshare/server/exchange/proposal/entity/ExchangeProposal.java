package com.weshare.server.exchange.proposal.entity;

import com.weshare.server.common.entity.BaseTimeEntity;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.exchange.entity.ExchangePost;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exchange_proposal")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ExchangeProposal extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_post_id", nullable = false)
    private ExchangePost exchangePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_candidate_post", nullable = false)
    private ExchangeCandidatePost exchangeCandidatePost;

    @Enumerated(EnumType.STRING)
    @Column(name = "proposal_status",nullable = false)
    private ProposalStatus proposalStatus;

    @Builder
    public ExchangeProposal(ExchangePost exchangePost, ExchangeCandidatePost exchangeCandidatePost, ProposalStatus proposalStatus) {
        this.exchangePost = exchangePost;
        this.exchangeCandidatePost = exchangeCandidatePost;
        this.proposalStatus = proposalStatus;
    }
}
