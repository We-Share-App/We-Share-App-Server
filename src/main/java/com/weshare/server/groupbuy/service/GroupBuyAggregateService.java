package com.weshare.server.groupbuy.service;

import com.weshare.server.aws.s3.service.S3Service;
import com.weshare.server.groupbuy.dto.GroupBuyPostCreateRequest;
import com.weshare.server.groupbuy.dto.GroupBuyPostCreateResponse;
import com.weshare.server.groupbuy.dto.GroupBuyPostDto;
import com.weshare.server.groupbuy.dto.GroupBuyPostFilterDto;
import com.weshare.server.groupbuy.entity.GroupBuyParticipant;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.service.image.GroupBuyPostImageService;
import com.weshare.server.groupbuy.service.participant.GroupBuyParticipantService;
import com.weshare.server.groupbuy.service.post.GroupBuyPostService;
import com.weshare.server.groupbuy.service.post.GroupBuyPostViewService;
import com.weshare.server.payment.PaymentStatus;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupBuyAggregateService {
    private final UserService userService;
    private final GroupBuyPostService groupBuyPostService;
    private final GroupBuyParticipantService groupBuyParticipantService;
    private final GroupBuyPostImageService groupBuyPostImageService;
    private final GroupBuyPostViewService groupBuyPostViewService;
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

        // GroupBuyPost 잔여개수 최신화 하기
        groupBuyPostService.updateRemainQuantity(groupBuyPost,List.of(PaymentStatus.POST_OWNER));

        return new GroupBuyPostCreateResponse(true,groupBuyPost.getId());
    }

    @Transactional
    public GroupBuyPostDto getOnePostWithImage(Long groupBuyPostId, CustomOAuth2User principal){
        // 게시글 찾기
        GroupBuyPost groupBuyPost = groupBuyPostService.findPostById(groupBuyPostId);
        // 게시글 이미지 찾기
        List<String> presignedUrlList = groupBuyPostImageService.getImageKey(groupBuyPost).stream().map(s3Service::getPresignedUrl).collect(Collectors.toList());
        // 좋아요 개수 카운트
        Long likes = groupBuyPostService.getLikeCount(groupBuyPost);
        Boolean isUserLiked = groupBuyPostService.isUserLikedPost(groupBuyPost,principal);
        Long viewCount = groupBuyPostViewService.updateViewCount(groupBuyPostId, principal);
        Boolean isYours = groupBuyPostService.isPostWriter(groupBuyPost,principal);

        GroupBuyPostDto groupBuyPostDto = GroupBuyPostDto.builder()
                .groupBuyPostId(groupBuyPost.getId())
                .itemName(groupBuyPost.getItemName())
                .itemDescription(groupBuyPost.getItemDescription())
                .itemUrl(groupBuyPost.getItemUrl())
                .itemPrice(groupBuyPost.getItemPrice())
                .totalQuantity(groupBuyPost.getItemQuantity())
                .remainQuantity(groupBuyPost.getRemainQuantity())
                .imageUrlList(presignedUrlList)
                .categoryName(groupBuyPost.getCategory().getCategoryName())
                .expirationDateTime(groupBuyPost.getRecruitingExpirationDate())
                .viewCount(viewCount)
                .likes(likes)
                .isUserLiked(isUserLiked)
                .isYours(isYours)
                .userNickname(groupBuyPost.getUser().getNickname())
                .build();
        return groupBuyPostDto;
    }

    @Transactional
    public List<GroupBuyPostDto> getPostsWithImage(GroupBuyPostFilterDto request, CustomOAuth2User principal){
        // 포스트 엔티티 필터링 전체 조회
        List<GroupBuyPost> groupBuyPostList = groupBuyPostService.getFilteredGroupBuyPost(request);

        //게시물 데이터 리스트 객체
        List<GroupBuyPostDto> groupBuyPostDtoList = new ArrayList<>();

        for(GroupBuyPost groupBuyPost : groupBuyPostList){
            // 각각의 포스트 엔티티에 대하여 카테고리명을 가져옴
            String categoryName = groupBuyPost.getCategory().getCategoryName();
            // 각각의 포스트 엔티티에 대하여 이미지키를 찾아와 presigned URL 획득하기
            List<String> presignedUrlList = groupBuyPostImageService.getImageKey(groupBuyPost).stream().map(s3Service::getPresignedUrl).collect(Collectors.toList());
            Long likes = groupBuyPostService.getLikeCount(groupBuyPost);
            Boolean isUserLiked = groupBuyPostService.isUserLikedPost(groupBuyPost,principal);
            Long viewCount = groupBuyPostViewService.getViewCount(groupBuyPost.getId());
            Integer participants = groupBuyPostService.countParticipants(groupBuyPost);

            GroupBuyPostDto groupBuyPostDto = GroupBuyPostDto.builder()
                    .groupBuyPostId(groupBuyPost.getId())
                    .itemName(groupBuyPost.getItemName())
                    .imageUrlList(presignedUrlList)
                    .categoryName(categoryName)
                    .totalQuantity(groupBuyPost.getItemQuantity())
                    .remainQuantity(groupBuyPost.getRemainQuantity())
                    .expirationDateTime(groupBuyPost.getRecruitingExpirationDate())
                    .likes(likes)
                    .viewCount(viewCount)
                    .isUserLiked(isUserLiked)
                    .participantCount(participants)
                    .build();
            groupBuyPostDtoList.add(groupBuyPostDto);
        }

        return  groupBuyPostDtoList;
    }
}
