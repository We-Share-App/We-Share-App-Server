package com.weshare.server.exchange.service.post;

import com.weshare.server.exchange.entity.ItemCondition;
import com.weshare.server.exchange.dto.ExchangePostRequest;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.exception.post.ExchangePostException;
import com.weshare.server.exchange.exception.post.ExchangePostExceptions;
import com.weshare.server.exchange.repository.ExchangePostRepository;
import com.weshare.server.location.entity.Location;
import com.weshare.server.location.repository.LocationRepository;
import com.weshare.server.user.entity.User;
import com.weshare.server.user.exception.UserException;
import com.weshare.server.user.exception.UserExceptions;
import com.weshare.server.user.jwt.oauthJwt.dto.CustomOAuth2User;
import com.weshare.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ExchangePostServiceImpl implements ExchangePostService{
    private final UserRepository userRepository;
    private final ExchangePostRepository exchangePostRepository;
    private final LocationRepository locationRepository;
    @Override
    public ExchangePost createExchangePost(ExchangePostRequest request, CustomOAuth2User principal) {

        User user = userRepository.findByUsername(principal.getUsername());
        if(user == null){
            throw new UserException(UserExceptions.USER_NOT_FOUND);
        }
        Location location = locationRepository.findById(request.getLocationId()).orElseThrow(()-> new ExchangePostException(ExchangePostExceptions.NOT_EXIST_EXCHANGE_POST));
        LocalDateTime expirationDateTime = LocalDateTime.now().minusHours(request.getActiveDuration());
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
}
