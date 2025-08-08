package com.weshare.server.groupbuy.service.like;

import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.entity.GroupBuyPostLike;
import com.weshare.server.user.entity.User;

public interface GroupBuyPostLikeService {
    GroupBuyPostLike addPostLike(GroupBuyPost groupBuyPost, User user);
    GroupBuyPostLike deletePostLike(GroupBuyPost groupBuyPost, User user);
}
