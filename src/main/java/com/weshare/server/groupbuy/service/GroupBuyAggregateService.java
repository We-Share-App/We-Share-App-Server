package com.weshare.server.groupbuy.service;

import com.weshare.server.aws.s3.service.S3Service;
import com.weshare.server.groupbuy.dto.GroupBuyPostCreateRequest;
import com.weshare.server.groupbuy.dto.GroupBuyPostCreateResponse;
import com.weshare.server.groupbuy.entity.GroupBuyParticipant;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.service.image.GroupBuyPostImageService;
import com.weshare.server.groupbuy.service.participant.GroupBuyParticipantService;
import com.weshare.server.groupbuy.service.post.GroupBuyPostService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupBuyAggregateService {
    private final GroupBuyPostService groupBuyPostService;
    private final GroupBuyParticipantService groupBuyParticipantService;
    private final GroupBuyPostImageService groupBuyPostImageService;
    private final S3Service s3Service;
    private static final String directory = "groupBuy";

    @Transactional
    public GroupBuyPostCreateResponse createGroupBuyPost(GroupBuyPostCreateRequest groupBuyPostCreateRequest, List<MultipartFile> images, CustomOAuth2User principal){
        // GroupBuyPost 게시글 등록하기
         GroupBuyPost groupBuyPost = groupBuyPostService.createGroupBuyPost(groupBuyPostCreateRequest,principal);
        //GroupBuyPostImage 등록하기
        for(MultipartFile img : images){
            String key = s3Service.uploadImage(directory,img);
            groupBuyPostImageService.saveImageKey(key,groupBuyPost);
        }
        //GroupBuyParticipant 등록하기
        groupBuyParticipantService.addPostOwner(groupBuyPost,groupBuyPostCreateRequest.getWriterQuantity(),principal);

        return new GroupBuyPostCreateResponse(true,groupBuyPost.getId());
    }
}
