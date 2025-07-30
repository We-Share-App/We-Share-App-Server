package com.weshare.server.exchange.service.post;

import com.weshare.server.exchange.dto.ExchangePostFilterRequest;
import com.weshare.server.exchange.entity.ItemCondition;
import com.weshare.server.exchange.dto.ExchangePostCreateRequest;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.exception.post.ExchangePostExceptions;
import com.weshare.server.exchange.repository.ExchangePostLikeRepository;
import com.weshare.server.exchange.repository.ExchangePostRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangePostServiceImpl implements ExchangePostService{
    private final UserRepository userRepository;
    private final ExchangePostRepository exchangePostRepository;
    private final ExchangePostLikeRepository exchangePostLikeRepository;
    private final LocationRepository locationRepository;
    @Override
    @Transactional
    public ExchangePost createExchangePost(ExchangePostCreateRequest request, CustomOAuth2User principal) {

        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()->new UserException(UserExceptions.USER_NOT_FOUND));
        Location location = locationRepository.findById(request.getLocationId()).orElseThrow(()-> new LocationException(LocationExceptions.NOT_EXIST_LOCATION_ID));
        LocalDateTime expirationDateTime = LocalDateTime.now().plusHours(request.getActiveDuration());
        ItemCondition itemCondition = ItemCondition.stringToEnum(request.getItemCondition());

        ExchangePost exchangePost = ExchangePost.builder()
                .itemName(request.getItemName())
                .itemDescription(request.getItemDescription())
                .recruitingExpirationDate(expirationDateTime)
                .itemCondition(itemCondition)
                .user(user)
                .location(location)
                .build();
        return exchangePostRepository.save(exchangePost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExchangePost> getFilteredExchangePost(ExchangePostFilterRequest request) {
        // 1) lastPostId가 없으면 커서 조건을 아예 생략
        ExchangePost lastPost = null;
        if (request.getLastPostId() != null) {
            lastPost = exchangePostRepository.findById(request.getLastPostId())
                    .orElseThrow(() ->
                            new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST)
                    );
        }

        // 2) sortDirection을 Enum 바인딩(대소문자 자동 처리)
        Sort.Direction direction = request.getSortDirection();

        // 3) 스펙 빌드 (null인 lastPost는 buildSpec 내부에서 무시하도록 구현)
        Specification<ExchangePost> spec =
                ExchangePostSpecification.buildSpec(request, lastPost);

        // 4) 페이징 파라미터 유연화
        int page = Optional.ofNullable(request.getPage()).orElse(0);
        int size = Optional.ofNullable(request.getSize()).orElse(10);
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, "createdAt", "id")
        );

        return exchangePostRepository.findAll(spec, pageable)
                .getContent();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getLikeCount(ExchangePost exchangePost) {
        return exchangePostLikeRepository.countByExchangePost(exchangePost);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean isUserLikedPost(CustomOAuth2User principal, ExchangePost exchangePost) {
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()->new UserException(UserExceptions.USER_NOT_FOUND));
        return exchangePostLikeRepository.existsByUserAndExchangePost(user, exchangePost);
    }
}
