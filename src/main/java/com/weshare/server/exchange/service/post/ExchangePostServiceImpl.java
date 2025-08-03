package com.weshare.server.exchange.service.post;

import com.weshare.server.exchange.dto.ExchangePostFilterDto;
import com.weshare.server.exchange.entity.ItemCondition;
import com.weshare.server.exchange.dto.ExchangePostCreateRequest;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostStatus;
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
import java.util.Objects;
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
                .exchangePostStatus(ExchangePostStatus.OPEN) // 포스트 상태: "활성화"
                .build();
        return exchangePostRepository.save(exchangePost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExchangePost> getFilteredExchangePost(ExchangePostFilterDto request) {

        // 0) itemCondition 파라미터가 있으면 즉시 파싱
        String itenConditionString = request.getItemCondition();
        if (itenConditionString != null && !itenConditionString.isBlank()) {
            ItemCondition itemCondition = ItemCondition.stringToEnum(itenConditionString); //  (잘못된 값은 여기서 exception 발생)
            request.setItemCondition(itemCondition.name()); // 소문자|| 대문자로 들어온 올바른 키워드 => 대문자로 확정 변경
        }

        // 1) lastPostId가 없거나 유효하지 않으면 커서 조건을 아예 생략
        ExchangePost lastPost = null;
        Long lastPostId = request.getLastPostId();
        if (lastPostId != null && lastPostId > 0) {
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

    @Override
    public ExchangePost findExchangePost(Long id) {
        return exchangePostRepository.findById(id).orElseThrow(()-> new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST));
    }

    @Override
    public Boolean isPostWriter(ExchangePost exchangePost, CustomOAuth2User principal) {
        User user = userRepository.findByUsername(principal.getUsername()).orElseThrow(()-> new UserException(UserExceptions.USER_NOT_FOUND));
        return Objects.equals(exchangePost.getUser().getId(), user.getId());
    }
}
