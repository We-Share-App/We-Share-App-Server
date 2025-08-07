package com.weshare.server.groupbuy.repository;

import com.weshare.server.groupbuy.entity.GroupBuyParticipant;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.payment.PaymentStatus;
import com.weshare.server.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GroupBuyParticipantRepository extends JpaRepository<GroupBuyParticipant,Long> {
    @Query("""
        SELECT COALESCE(SUM(p.quantity), 0)
          FROM GroupBuyParticipant p
         WHERE p.groupBuyPost.id = :postId
           AND p.paymentStatus IN :statuses
        """)
    Integer sumQuantityByPostIdAndStatuses(
            @Param("postId") Long postId,
            @Param("statuses") List<PaymentStatus> statuses
    );

    Integer countByGroupBuyPostAndPaymentStatusIn(GroupBuyPost groupBuyPost, Collection<PaymentStatus> statuses);

    Optional<GroupBuyParticipant> findByGroupBuyPostAndUser(GroupBuyPost groupBuyPost, User user);
}
