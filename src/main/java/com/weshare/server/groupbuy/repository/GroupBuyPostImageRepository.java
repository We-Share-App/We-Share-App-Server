package com.weshare.server.groupbuy.repository;

import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.entity.GroupBuyPostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupBuyPostImageRepository extends JpaRepository<GroupBuyPostImage,Long> {
    List<GroupBuyPostImage> findAllByGroupBuyPost(GroupBuyPost groupBuyPost);
}
