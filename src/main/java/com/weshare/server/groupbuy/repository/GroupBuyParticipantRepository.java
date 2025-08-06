package com.weshare.server.groupbuy.repository;

import com.weshare.server.groupbuy.entity.GroupBuyParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupBuyParticipantRepository extends JpaRepository<GroupBuyParticipant,Long> {
}
