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
@Table(name = "exchange_proposal",uniqueConstraints = @UniqueConstraint(columnNames={"exchange_post_id", "exchange_candidate_post_id"}))
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
    @JoinColumn(name = "exchange_candidate_post_id", nullable = false)
    private ExchangeCandidatePost exchangeCandidatePost;

    @Enumerated(EnumType.STRING)
    @Column(name = "proposal_status",nullable = false)
    private ExchangeProposalStatus exchangeProposalStatus;

    @Builder
    public ExchangeProposal(ExchangePost exchangePost, ExchangeCandidatePost exchangeCandidatePost, ExchangeProposalStatus exchangeProposalStatus) {
        this.exchangePost = exchangePost;
        this.exchangeCandidatePost = exchangeCandidatePost;
        this.exchangeProposalStatus = exchangeProposalStatus;
    }

    public ExchangeProposal updateExchangeProposalStatus(ExchangeProposalStatus exchangeProposalStatus){
        this.exchangeProposalStatus = exchangeProposalStatus;
        return this;
    }

}
