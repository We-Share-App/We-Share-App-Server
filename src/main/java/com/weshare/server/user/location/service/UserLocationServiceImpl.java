package com.weshare.server.user.location.service;

import com.weshare.server.location.entity.Location;
import com.weshare.server.location.repository.LocationRepository;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.location.dto.UserLocationRegistrationRequest;
import com.weshare.server.user.location.entity.UserLocation;
import com.weshare.server.user.location.exception.UserLocationException;
import com.weshare.server.user.location.exception.UserLocationExceptions;
import com.weshare.server.user.location.repository.UserLocationRepository;
import com.weshare.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLocationServiceImpl implements UserLocationService {
    private final UserRepository userRepository;
    private final UserLocationRepository userLocationRepository;
    private final LocationRepository locationRepository;
    private static final long MAX_LOCATIONS = 2L;

    /**
     * 1) 사용자 로드
     * 2) 저장 개수 제한 검사
     * 3) Location 조회 또는 생성 (동시성 처리 포함)
     * 4) 중복 등록 방지
     * 5) UserLocation 저장 (동시성 처리 포함)
     */
    @Override
    @Transactional
    public UserLocation createUserLocation(UserLocationRegistrationRequest request, CustomOAuth2User principal) {
        User user = loadUser(principal); // user 조회
        ensureUnderLimit(user); // 유저 위치 등록 개수 초과 여부 확인
        Location location = findOrCreateLocation(request); // Location 등록 또는 찾기
        preventDuplicate(user, location); // user_location 중복 등록 방지

        // user_location 등록 시도
        UserLocation userLocation = UserLocation.builder()
                .user(user)
                .location(location)
                .build();

        try {
            UserLocation saved = userLocationRepository.save(userLocation);
            log.info("UserLocation saved: userId={}, locationId={}, id={}",
                    user.getId(), location.getId(), saved.getId());
            return saved;
        } catch (DataIntegrityViolationException e) {
            // 이미 다른 트랜잭션에서 동일 조합 저장 시도 (동시성 문제 발생시)
            log.debug("Concurrency conflict on UserLocation save: userId={}, locationId={}",
                    user.getId(), location.getId(), e);
            throw new UserLocationException(UserLocationExceptions.LOCATION_ALREADY_REGISTERED);
        }
    }

    // user 엔티티 찾기
    private User loadUser(CustomOAuth2User principal) {
        return userRepository.findByUsername(principal.getUsername()).orElseThrow(() -> new UserException(UserExceptions.USER_NOT_FOUND));
    }

    // 유저 위치 등록 개수 초과 여부 확인
    private void ensureUnderLimit(User user) {
        long count = userLocationRepository.countByUser(user);
        if (count >= MAX_LOCATIONS) {
            log.warn("User {} has reached location limit: {} entries", user.getId(), count);
            throw new UserLocationException(UserLocationExceptions.LOCATION_LIMIT_EXCEEDED);
        }
    }

    /**
     * Location 조회 또는 생성
     * - DataIntegrityViolationException 시 재조회
     */
    private Location findOrCreateLocation(UserLocationRegistrationRequest request) {
        String state = request.getStateName();
        String city = request.getCityName();
        String town = request.getTownName();

        return locationRepository.findByStateNameAndCityNameAndTownName(state, city, town).orElseGet(() -> {
                    try {
                        return locationRepository.save(Location.builder().stateName(state).cityName(city).townName(town).build());
                    } catch (DataIntegrityViolationException e) {
                        // 다른 트랜잭션에서 이미 생성된 경우
                        log.debug("Concurrency conflict on Location save: {}/{}/{}, retrieving existing record", state, city, town, e);
                        return locationRepository.findByStateNameAndCityNameAndTownName(state, city, town).orElseThrow(() -> new UserLocationException(UserLocationExceptions.USER_LOCATION_CREATION_FAILED));
                    }
                });
    }

    private void preventDuplicate(User user, Location location) {
        boolean exists = userLocationRepository.existsByUserAndLocation(user, location);
        if (exists) {
            log.warn("Duplicate UserLocation attempt: userId={}, locationId={}", user.getId(), location.getId());throw new UserLocationException(UserLocationExceptions.LOCATION_ALREADY_REGISTERED);
        }
    }
}


