package com.weshare.server.groupbuy.repository;

import com.weshare.server.groupbuy.entity.GroupBuyParticipant;
import com.weshare.server.payment.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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
}
