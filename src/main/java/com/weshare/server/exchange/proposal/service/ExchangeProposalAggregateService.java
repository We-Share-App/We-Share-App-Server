package com.weshare.server.exchange.proposal.service;

import com.weshare.server.aws.s3.service.S3Service;
import com.weshare.server.exchange.candidate.dto.ExchangeCandidatePostDto;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidatePost;
import com.weshare.server.exchange.candidate.entity.ExchangeCandidateStatus;
import com.weshare.server.exchange.candidate.exception.ExchangeCandidatePostException;
import com.weshare.server.exchange.candidate.exception.ExchangeCandidatePostExceptions;
import com.weshare.server.exchange.candidate.service.image.ExchangeCandidatePostImageService;
import com.weshare.server.exchange.candidate.service.post.ExchangeCandidatePostService;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostStatus;
import com.weshare.server.exchange.proposal.dto.ExchangeProposalRequest;
import com.weshare.server.exchange.proposal.dto.ExchangeProposalResponse;
import com.weshare.server.exchange.proposal.entity.ExchangeProposal;
import com.weshare.server.exchange.proposal.exception.ExchangeProposalException;
import com.weshare.server.exchange.proposal.exception.ExchangeProposalExceptions;
import com.weshare.server.exchange.service.category.ExchangePostCategoryService;
import com.weshare.server.exchange.service.post.ExchangePostService;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExchangeProposalAggregateService {
    private final UserService userService;
    private final ExchangePostService exchangePostService;
    private final ExchangePostCategoryService exchangePostCategoryService;
    private final ExchangeCandidatePostService exchangeCandidatePostService;
    private final ExchangeCandidatePostImageService exchangeCandidatePostImageService;
    private final ExchangeProposalService exchangeProposalService;
    private final S3Service s3Service;
    public List<String> getExchangePostWishCategoryList(Long exchangeId){
        ExchangePost exchangePost = exchangePostService.findExchangePost(exchangeId);
        return exchangePostCategoryService.getExchangePostCategoryNameList(exchangePost);
    }

    public List<ExchangeCandidatePostDto> getAllExchangeCandidatePostDtoList(Long exchangePostId, CustomOAuth2User principal){
        //게시물 데이터 리스트 객체
        List<ExchangeCandidatePostDto> exchangeCandidatePostDtoList = new ArrayList<>();
        //exchangePostId에 대응하는 ExchangeCandidatePost 리스트 가져오기
        List<ExchangeCandidatePost> exchangeCandidatePostList = exchangeCandidatePostService.getAllUserEnrolledExchangeCandidatePost(exchangePostId,principal);
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

    @Transactional
    public ExchangeProposalResponse proposeExchange(ExchangeProposalRequest exchangeProposalRequest, CustomOAuth2User customOAuth2User) {
        // 1) 요청 사용자 조회
        User user = userService.findUserByUsername(customOAuth2User.getUsername());

        // 2) 대상 교환 게시글 조회
        ExchangePost exchangePost = exchangePostService.findExchangePost(exchangeProposalRequest.getTargetExchangePostId());

        // 3) 본인 게시글 제안 금지
        if (Objects.equals(user.getId(), exchangePost.getUser().getId())) {
            throw new ExchangeProposalException(ExchangeProposalExceptions.CANNOT_PROPOSE_YOURSELF);
        }

        // 4) 이미 종료된 게시글 제안 금지
        if (exchangePost.getExchangePostStatus() == ExchangePostStatus.CLOSED) {
            throw new ExchangeProposalException(ExchangeProposalExceptions.ALREADY_CLOSED_EXCHANGE_POST);
        }

        // 5) 교환 후보 아이템 일괄 조회
        List<ExchangeCandidatePost> exchangeCandidatePostList = exchangeCandidatePostService.findAllByExchangeCandidateId(exchangeProposalRequest.getExchangeCandidateIdList());
        List<Long> exchangeCandidatePostIdList = exchangeCandidatePostList.stream().map(ExchangeCandidatePost::getId).collect(Collectors.toList());

        // 6) 이미 제안된 후보 아이템 ID 배치 조회
        Set<Long> alreadyProposedCandidatePostIds = new HashSet<>(exchangeProposalService.findAlreadyProposedCandidatePostIdList(exchangePost.getId(), exchangeCandidatePostIdList));

        // 7) 후보 아이템별 유효성 검증
        for (ExchangeCandidatePost exchangeCandidatePost : exchangeCandidatePostList) {
            // 7-1) 거래 가능 상태 확인
            if (exchangeCandidatePost.getExchangeCandidateStatus() != ExchangeCandidateStatus.AVAILABLE) {
                throw new ExchangeCandidatePostException(ExchangeCandidatePostExceptions.NOT_AVAILABLE_EXCHANGE_CANDIDATE_POST);
            }

            // 7-2) 중복 제안 방지
            if (alreadyProposedCandidatePostIds.contains(exchangeCandidatePost.getId())) {
                throw new ExchangeProposalException(ExchangeProposalExceptions.ALREADY_PROPOSED_CANDIDATE_TO_THIS_EXCHANGE_POST);
            }

            // 7-3) 후보 아이템 소유자(제안자) 일치 여부 확인
            if (!Objects.equals(exchangeCandidatePost.getUser().getId(), user.getId())) {
                throw new ExchangeProposalException(ExchangeProposalExceptions.NOT_A_CANDIDATE_POST_OWNER);
            }
        }

        // 8) 교환 제안 등록 및 생성된 제안 ID 수집
        List<Long> createdExchangeProposalIdList = new ArrayList<>();
        for (ExchangeCandidatePost exchangeCandidatePost : exchangeCandidatePostList) {
            ExchangeProposal createdProposal = exchangeProposalService.registerExchangeProposal(exchangePost.getId(), exchangeCandidatePost.getId());
            createdExchangeProposalIdList.add(createdProposal.getId());
        }

        // 9) 응답 반환
        return new ExchangeProposalResponse(true, createdExchangeProposalIdList, exchangePost.getId(), exchangeCandidatePostIdList);
    }


    public ExchangeProposalResponse doProposal(ExchangeProposalRequest request, CustomOAuth2User principal){
        User user = userService.findUserByUsername(principal.getUsername());
        ExchangePost exchangePost = exchangePostService.findExchangePost(request.getTargetExchangePostId());
        List<ExchangeCandidatePost> exchangeCandidatePostList = exchangeCandidatePostService.findAllByExchangeCandidateId(request.getExchangeCandidateIdList());

        // 교환 요청자와 공개 교환 게시글 작성자가 동일인물인 경우
        if(Objects.equals(user.getId(), exchangePost.getUser().getId())){
            throw new ExchangeProposalException(ExchangeProposalExceptions.CANNOT_PROPOSE_YOURSELF);
        }
        // 대상 공개 물품교환 게시글 상태가 이미 CLOSED 상태인 경우
        if(exchangePost.getExchangePostStatus() == ExchangePostStatus.CLOSED){
            throw new ExchangeProposalException(ExchangeProposalExceptions.ALREADY_CLOSED_EXCHANGE_POST);
        }

        // 물품 거래가 불가능한 물품 교환 후보가 있는지 점검
        for(ExchangeCandidatePost exchangeCandidatePost : exchangeCandidatePostList){
            if(exchangeCandidatePost.getExchangeCandidateStatus() == ExchangeCandidateStatus.TRADED){
                throw new ExchangeCandidatePostException(ExchangeCandidatePostExceptions.NOT_AVAILABLE_EXCHANGE_CANDIDATE_POST);
            }
            // 이미 해당 사용자가 교환 신청을 한 상품인 경우 중복 등록 방지
            if(exchangeProposalService.isAlreadyProposedCandidate(exchangePost,exchangeCandidatePost)){
                throw new ExchangeProposalException(ExchangeProposalExceptions.ALREADY_PROPOSED_CANDIDATE_TO_THIS_EXCHANGE_POST);
            }

            // 교환 요청자와 교환 후보글의 작성자 일치 여부 확인
            if(!Objects.equals(exchangeCandidatePost.getUser().getId(), user.getId())){
                throw new ExchangeProposalException(ExchangeProposalExceptions.NOT_A_CANDIDATE_POST_OWNER);
            }

        }

        // 교환 등록 및 교환 등록 ID 수집
        List<Long> exchangeProposalIdList = new ArrayList<>();
        for(ExchangeCandidatePost exchangeCandidatePost : exchangeCandidatePostList){
            ExchangeProposal exchangeProposal = exchangeProposalService.registerExchangeProposal(exchangePost.getId(), exchangeCandidatePost.getId());
            exchangeProposalIdList.add(exchangeProposal.getId());
        }

        ExchangeProposalResponse response = new ExchangeProposalResponse(true,exchangeProposalIdList, exchangePost.getId(), request.getExchangeCandidateIdList());
        return response;
    }

}
