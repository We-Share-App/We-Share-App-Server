package com.weshare.server.exchange.service.post;


import com.weshare.server.exchange.dto.ExchangePostFilterRequest;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ItemCondition;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExchangePostSpecification {

    public static Specification<ExchangePost> buildSpec(ExchangePostFilterRequest request,
                                                        ExchangePost lastPost) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1) 필수: 지역
            predicates.add(cb.equal(
                    root.get("location").get("id"),
                    request.getLocationId()
            ));

            // 2) 게시글이 아직 모집 기간(recruitingExpirationDate)을 지나지 않았을 것
            predicates.add(cb.greaterThanOrEqualTo(
                    root.get("recruitingExpirationDate"),
                    cb.currentTimestamp()
            ));

            // 3) 선택: 카테고리
            if (request.getCategoryId() != null) {
                predicates.add(cb.equal(
                        root.get("category").get("id"),
                        request.getCategoryId()
                ));
            }

            // 4) 선택: 상품 상태
            if (request.getItemCondition() != null) {
                predicates.add(cb.equal(
                        root.get("itemCondition"),
                        ItemCondition.valueOf(request.getItemCondition())
                ));
            }

            // 5) 커서 기반 페이징
            if (lastPost != null) {
                boolean asc = request.getSortDirection() == Sort.Direction.ASC;

                Predicate timePredicate;
                Predicate idPredicate;

                if (asc) {
                    // ASC: createdAt > lastCreatedAt OR (createdAt = lastCreatedAt AND id > lastId)
                    timePredicate = cb.greaterThan(
                            root.get("createdAt"),
                            lastPost.getCreatedAt()
                    );
                    idPredicate = cb.and(
                            cb.equal(root.get("createdAt"), lastPost.getCreatedAt()),
                            cb.greaterThan(root.get("id"), lastPost.getId())
                    );
                } else {
                    // DESC: createdAt < lastCreatedAt OR (createdAt = lastCreatedAt AND id < lastId)
                    timePredicate = cb.lessThan(
                            root.get("createdAt"),
                            lastPost.getCreatedAt()
                    );
                    idPredicate = cb.and(
                            cb.equal(root.get("createdAt"), lastPost.getCreatedAt()),
                            cb.lessThan(root.get("id"), lastPost.getId())
                    );
                }

                predicates.add(cb.or(timePredicate, idPredicate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

