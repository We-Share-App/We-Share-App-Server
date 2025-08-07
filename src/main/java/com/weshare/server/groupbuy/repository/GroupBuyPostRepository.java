package com.weshare.server.groupbuy.repository;

import com.weshare.server.groupbuy.dto.GroupBuyPostCreateRequest;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GroupBuyPostRepository extends JpaRepository<GroupBuyPost,Long>, JpaSpecificationExecutor<GroupBuyPost> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM GroupBuyPost p WHERE p.id = :id")
    Optional<GroupBuyPost> findByIdWithLock(@Param("id") Long id);

}
