package com.weshare.server.groupbuy.service.post;

import com.weshare.server.category.entity.Category;
import com.weshare.server.category.exception.CategoryException;
import com.weshare.server.category.exception.CategoryExceptions;
import com.weshare.server.category.repository.CategoryRepository;
import com.weshare.server.groupbuy.dto.GroupBuyPostCreateRequest;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import com.weshare.server.groupbuy.exception.GroupBuyPostException;
import com.weshare.server.groupbuy.exception.GroupBuyPostExceptions;
import com.weshare.server.groupbuy.repository.GroupBuyPostRepository;
import com.weshare.server.location.entity.Location;
import com.weshare.server.location.exception.LocationException;
import com.weshare.server.location.exception.LocationExceptions;
import com.weshare.server.location.repository.LocationRepository;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GroupBuyPostServiceImpl implements GroupBuyPostService{
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final GroupBuyPostRepository groupBuyPostRepository;

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
                .shippingFee(request.getShippingFee())
                .recruitingExpirationDate(GroupBuyPostCreateRequest.dateToDateTimeWithEndOfDayTime(request.getExpirationDate()))
                .user(user)
                .category(category)
                .location(location)
                .build();

        return groupBuyPostRepository.save(groupBuyPost);
    }
}
