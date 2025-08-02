package com.weshare.server.exchange.proposal.service;

import com.weshare.server.aws.s3.service.S3Service;
import com.weshare.server.exchange.dto.ExchangeProposalPostDto;
import com.weshare.server.exchange.proposal.dto.ExchangeProposalRequest;
import com.weshare.server.exchange.proposal.dto.ExchangeProposalResponse;
import com.weshare.server.exchange.proposal.entity.ExchangeProposalPost;
import com.weshare.server.exchange.proposal.service.image.ExchangeProposalPostImageService;
import com.weshare.server.exchange.proposal.service.post.ExchangeProposalPostService;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeProposalPostAggregateService {
    private final ExchangeProposalPostService exchangeProposalPostService;
    private final ExchangeProposalPostImageService exchangeProposalPostImageService;
    private final S3Service s3Service;
    private static final String directory = "exchange-proposal";

    @Transactional
    public ExchangeProposalResponse createPostWithImage(ExchangeProposalRequest request, List<MultipartFile> images, CustomOAuth2User principal){
        // 게시글 업로드
        ExchangeProposalPost post = exchangeProposalPostService.createExchangeProposalPost(request,principal);

        //게시글 이미지 업로드 & 이미지 키 DB 저장 (exchange_proposal_post_image 테이블)
        for(MultipartFile img : images){
            String key = s3Service.uploadImage(directory,img);
            exchangeProposalPostImageService.saveImageKey(key,post);
        }
        return new ExchangeProposalResponse(true, post.getId());
    }

    @Transactional
    public List<ExchangeProposalPostDto> getAllProposalList(Long exchangePostId){

        //게시물 데이터 리스트 객체
        List<ExchangeProposalPostDto> exchangeProposalPostDtoList = new ArrayList<>();
        //exchangePostId에 대응하는 ExchangeProposalPost 리스트 가져오기
        List<ExchangeProposalPost> exchangeProposalPostList = exchangeProposalPostService.getAllExchangeProposalPost(exchangePostId);
        for(ExchangeProposalPost exchangeProposalPost : exchangeProposalPostList){
            // 각각의 엔티티에 대하여 이미지키를 찾아와 presigned URL 획득하기
            List<String> presignedUrlList = exchangeProposalPostImageService.getImageKey(exchangeProposalPost).stream().map(s3Service::getPresignedUrl).collect(Collectors.toList());
            ExchangeProposalPostDto exchangeProposalPostDto = ExchangeProposalPostDto.builder()
                    .id(exchangeProposalPost.getId())
                    .itemName(exchangeProposalPost.getItemName())
                    .itemDescription(exchangeProposalPost.getItemDescription())
                    .itemCondition(exchangeProposalPost.getItemCondition().getDescription())
                    .categoryName(exchangeProposalPost.getCategory().getCategoryName())
                    .imageUrlList(presignedUrlList)
                    .build();
            exchangeProposalPostDtoList.add(exchangeProposalPostDto);
        }
        return  exchangeProposalPostDtoList;
    }
}
