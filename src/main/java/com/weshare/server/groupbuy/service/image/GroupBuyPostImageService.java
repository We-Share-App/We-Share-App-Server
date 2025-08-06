package com.weshare.server.groupbuy.service.image;

import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.entity.GroupBuyPostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupBuyPostImageService {
    GroupBuyPostImage saveImageKey(String key, GroupBuyPost groupBuyPost);
    List<String> getImageKey(GroupBuyPost groupBuyPost);

}
