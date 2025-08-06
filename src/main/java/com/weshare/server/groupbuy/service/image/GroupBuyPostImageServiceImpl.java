package com.weshare.server.groupbuy.service.image;

import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.entity.GroupBuyPostImage;
import com.weshare.server.groupbuy.repository.GroupBuyPostImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupBuyPostImageServiceImpl implements GroupBuyPostImageService{
    private final GroupBuyPostImageRepository groupBuyPostImageRepository;
    @Override
    @Transactional
    public GroupBuyPostImage saveImageKey(String key, GroupBuyPost groupBuyPost) {
        GroupBuyPostImage groupBuyPostImage = GroupBuyPostImage.builder()
                .groupBuyPostImageKey(key)
                .groupBuyPost(groupBuyPost)
                .build();
        return groupBuyPostImageRepository.save(groupBuyPostImage);
    }

    @Override
    @Transactional
    public List<String> getImageKey(GroupBuyPost groupBuyPost) {
        // 해당 공동구매 게시글에 등록된 모든 이미지 엔티티 가져오기
        List<GroupBuyPostImage> groupBuyPostImageList = groupBuyPostImageRepository.findAllByGroupBuyPost(groupBuyPost);

        // 이미지 키 추출
        List<String> keyList = new ArrayList<>();
        for(GroupBuyPostImage groupBuyPostImage : groupBuyPostImageList){
            String key = groupBuyPostImage.getGroupBuyPostImageKey();
            keyList.add(key);
        }
        return keyList;
    }
}
