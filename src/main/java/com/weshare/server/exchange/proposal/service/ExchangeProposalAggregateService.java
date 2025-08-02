package com.weshare.server.exchange.proposal.service;

import com.weshare.server.aws.s3.service.S3Service;
import com.weshare.server.exchange.candidate.dto.ExchangeCandidatePostDto;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.exchange.candidate.service.image.ExchangeCandidatePostImageService;
import com.weshare.server.exchange.candidate.service.post.ExchangeCandidatePostService;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.service.category.ExchangePostCategoryService;
import com.weshare.server.exchange.service.post.ExchangePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeProposalAggregateService {
    private final ExchangePostService exchangePostService;
    private final ExchangePostCategoryService exchangePostCategoryService;
    private final ExchangeCandidatePostService exchangeCandidatePostService;
    private final ExchangeCandidatePostImageService exchangeCandidatePostImageService;
    private final S3Service s3Service;
    public List<String> getExchangePostWishCategoryList(Long exchangeId){
        ExchangePost exchangePost = exchangePostService.findExchangePost(exchangeId);
        return exchangePostCategoryService.getExchangePostCategoryNameList(exchangePost);
    }

    public List<ExchangeCandidatePostDto> getAllExchangeCandidatePostDtoList(Long exchangePostId){
        //게시물 데이터 리스트 객체
        List<ExchangeCandidatePostDto> exchangeCandidatePostDtoList = new ArrayList<>();
        //exchangePostId에 대응하는 ExchangeCandidatePost 리스트 가져오기
        List<ExchangeCandidatePost> exchangeCandidatePostList = exchangeCandidatePostService.getAllExchangeCandidatePost(exchangePostId);
        for(ExchangeCandidatePost exchangeCandidatePost : exchangeCandidatePostList){
            // 각각의 엔티티에 대하여 이미지키를 찾아와 presigned URL 획득하기
            List<String> presignedUrlList = exchangeCandidatePostImageService.getImageKey(exchangeCandidatePost).stream().map(s3Service::getPresignedUrl).collect(Collectors.toList());
            ExchangeCandidatePostDto exchangeCandidatePostDto = ExchangeCandidatePostDto.builder()
                    .id(exchangeCandidatePost.getId())
                    .itemName(exchangeCandidatePost.getItemName())
                    .itemDescription(exchangeCandidatePost.getItemDescription())
                    .itemCondition(exchangeCandidatePost.getItemCondition().getDescription())
                    .categoryName(exchangeCandidatePost.getCategory().getCategoryName())
                    .imageUrlList(presignedUrlList)
                    .writerNickname(exchangeCandidatePost.getUser().getNickname())
                    .build();
            exchangeCandidatePostDtoList.add(exchangeCandidatePostDto);
        }
        return exchangeCandidatePostDtoList;
    }
}
