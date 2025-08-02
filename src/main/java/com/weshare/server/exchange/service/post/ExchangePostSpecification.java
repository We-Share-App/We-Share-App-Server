package com.weshare.server.exchange.service.post;


import com.weshare.server.exchange.dto.ExchangePostFilterDto;
import com.weshare.server.exchange.entity.ExchangePost;
import com.weshare.server.exchange.entity.ExchangePostCategory;
import com.weshare.server.exchange.entity.ItemCondition;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ExchangePostSpecification {

    public static Specification<ExchangePost> buildSpec(ExchangePostFilterDto request,
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

            // 3) 카테고리 필터 (서브쿼리)
            if (request.getCategoryId() != null) {
                Subquery<Long> sub = query.subquery(Long.class);
                Root<ExchangePostCategory> epc = sub.from(ExchangePostCategory.class);
                sub.select(epc.get("exchangePost").get("id"))
                        .where(cb.equal(epc.get("category").get("id"), request.getCategoryId()));

                predicates.add(root.get("id").in(sub));
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

