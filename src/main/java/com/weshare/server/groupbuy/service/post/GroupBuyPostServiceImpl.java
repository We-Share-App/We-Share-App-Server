package com.weshare.server.groupbuy.service.post;

import com.weshare.server.category.entity.Category;
import com.weshare.server.category.exception.CategoryException;
import com.weshare.server.category.exception.CategoryExceptions;
import com.weshare.server.category.repository.CategoryRepository;
import com.weshare.server.groupbuy.dto.GroupBuyPostCreateRequest;
import com.weshare.server.groupbuy.dto.GroupBuyPostFilterDto;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.exception.GroupBuyPostException;
import com.weshare.server.groupbuy.exception.GroupBuyPostExceptions;
import com.weshare.server.groupbuy.repository.GroupBuyParticipantRepository;
import com.weshare.server.groupbuy.repository.GroupBuyPostLikeRepository;
import com.weshare.server.groupbuy.repository.GroupBuyPostRepository;
import com.weshare.server.groupbuy.service.GroupBuyPostSpecification;
import com.weshare.server.location.entity.Location;
import com.weshare.server.location.exception.LocationException;
import com.weshare.server.location.exception.LocationExceptions;
import com.weshare.server.location.repository.LocationRepository;
import com.weshare.server.payment.PaymentStatus;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupBuyPostServiceImpl implements GroupBuyPostService{
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final GroupBuyPostRepository groupBuyPostRepository;
    private final GroupBuyParticipantRepository groupBuyParticipantRepository;
    private final GroupBuyPostLikeRepository groupBuyPostLikeRepository;

    @Override
    @Transactional
    public GroupBuyPost createGroupBuyPost(GroupBuyPostCreateRequest request, CustomOAuth2User principal) {

        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()-> new UserException(UserExceptions.USER_NOT_FOUND));
        Location location = locationRepository.findById(request.getLocationId()).orElseThrow(()-> new LocationException(LocationExceptions.NOT_EXIST_LOCATION_ID));
        Category category = categoryRepository.findById(request.getItemCategoryId()).orElseThrow(()-> new CategoryException(CategoryExceptions.NOT_EXIST_CATEGORY_ID));

        // 예외처리 (입력으로 들어온 마감 날짜가 오늘보다 이른 경우 )
        if(request.getExpirationDate().isBefore(LocalDate.now())){
            throw new GroupBuyPostException(GroupBuyPostExceptions.EXPIRATION_DATE_EARLY_ERROR);
        }

        GroupBuyPost groupBuyPost = GroupBuyPost.builder()
                .itemName(request.getItemName())
                .itemDescription(request.getItemDescription())
                .itemUrl(request.getItemURL())
                .itemQuantity(request.getTotalQuantity())
                .itemPrice(request.getItemPrice())
                .remainQuantity(request.getTotalQuantity())
                .shippingFee(request.getShippingFee())
                .recruitingExpirationDate(GroupBuyPostCreateRequest.dateToDateTimeWithEndOfDayTime(request.getExpirationDate()))
                .user(user)
                .category(category)
                .location(location)
                .build();

        return groupBuyPostRepository.save(groupBuyPost);
    }

    @Override
    @Transactional
    public GroupBuyPost updateRemainQuantity(GroupBuyPost groupBuyPost, List<PaymentStatus> paymentStatusList) {
        Long groupBuyPostId = groupBuyPost.getId();
        Integer orderCount = groupBuyParticipantRepository.sumQuantityByPostIdAndStatuses(groupBuyPostId,paymentStatusList);
        groupBuyPost.updateRemainQuantity(groupBuyPost.getItemQuantity()-orderCount);
        return groupBuyPost;
    }

    @Override
    @Transactional
    public GroupBuyPost findPostById(Long id) {
        GroupBuyPost groupBuyPost = groupBuyPostRepository.findById(id).orElseThrow(()-> new GroupBuyPostException(GroupBuyPostExceptions.NOT_EXIST_GROUP_POST));
        return groupBuyPost;
    }

    @Override
    @Transactional
    public Long getLikeCount(GroupBuyPost groupBuyPost) {
        return groupBuyPostLikeRepository.countByGroupBuyPost(groupBuyPost);
    }

    @Override
    public Boolean isUserLikedPost(GroupBuyPost groupBuyPost, CustomOAuth2User principal) {
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()-> new UserException(UserExceptions.USER_NOT_FOUND));
        return groupBuyPostLikeRepository.existsByUserAndGroupBuyPost(user, groupBuyPost);
    }

    @Override
    public Boolean isPostWriter(GroupBuyPost groupBuyPost, CustomOAuth2User principal) {
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()-> new UserException(UserExceptions.USER_NOT_FOUND));
        return Objects.equals(groupBuyPost.getUser().getId(),user.getId());

    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupBuyPost> getFilteredGroupBuyPost(GroupBuyPostFilterDto request) {

        if (request.getPriceLowLimit() != null && request.getPriceHighLimit() != null && request.getPriceLowLimit() > request.getPriceHighLimit()) {
            throw new GroupBuyPostException(GroupBuyPostExceptions.PRICE_BAD_REQUEST);
        }
        if (request.getAmount() != null && request.getAmount() < 0) {
            throw new GroupBuyPostException(GroupBuyPostExceptions.AMOUNT_BAD_REQUEST);
        }

        // 1) lastPostId가 없거나 유효하지 않으면 커서 조건을 아예 생략
        GroupBuyPost lastPost = null;
        Long lastPostId = request.getLastPostId();
        if (lastPostId != null && lastPostId > 0){
            lastPost = groupBuyPostRepository.findById(lastPostId).orElseThrow(()-> new GroupBuyPostException(GroupBuyPostExceptions.NOT_EXIST_GROUP_POST));
        }

        // 2) sortDirection을 Enum 바인딩(대소문자 자동 처리)
        Sort.Direction direction = request.getSortDirection();

        // 3) 스펙 빌드 (null인 lastPost는 buildSpec 내부에서 무시하도록 구현)
        Specification<GroupBuyPost> spec = GroupBuyPostSpecification.buildSpec(request, lastPost);

        // 4) 페이징 파라미터 유연화
        int page = Optional.ofNullable(request.getPage()).orElse(0);
        int size = Optional.ofNullable(request.getSize()).orElse(10);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "createdAt", "id"));

        return groupBuyPostRepository.findAll(spec,pageable).getContent();
    }

    @Override
    public Integer countParticipants(GroupBuyPost groupBuyPost) {
        return groupBuyParticipantRepository.countByGroupBuyPostAndPaymentStatusIn(groupBuyPost,List.of(PaymentStatus.POST_OWNER,PaymentStatus.PAID));
    }
}
