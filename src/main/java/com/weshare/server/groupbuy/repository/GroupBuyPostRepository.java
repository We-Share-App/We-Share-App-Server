package com.weshare.server.groupbuy.repository;

import com.weshare.server.groupbuy.dto.GroupBuyPostCreateRequest;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupBuyPostRepository extends JpaRepository<GroupBuyPost,Long>, JpaSpecificationExecutor<GroupBuyPost> {
}
