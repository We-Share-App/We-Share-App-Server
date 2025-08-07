package com.weshare.server.groupbuy.service;

import com.weshare.server.groupbuy.dto.GroupBuyPostFilterDto;
import com.weshare.server.groupbuy.entity.GroupBuyPost;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GroupBuyPostSpecification {
    public static Specification<GroupBuyPost> buildSpec(GroupBuyPostFilterDto request, GroupBuyPost lastPost){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            // 1) 필수: 지역
            predicates.add(cb.equal(root.get("location").get("id"), request.getLocationId()));

            // 2) 게시글이 아직 모집 기간(recruitingExpirationDate)을 지나지 않았을 것
            predicates.add(cb.greaterThanOrEqualTo(root.get("recruitingExpirationDate"), cb.currentTimestamp()));

            // 3) 카테고리 필터링 (categoryId가 지정된 경우만)
            if (request.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), request.getCategoryId()));
            }

            // 4) 가격 필터링 (priceLowLimit ≤ price ≤ priceHighLimit)
            if (request.getPriceLowLimit() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("itemPrice"), request.getPriceLowLimit()));
            }
            if (request.getPriceHighLimit() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("itemPrice"), request.getPriceHighLimit()));
            }

            // 5) 수량 필터링 (remainQuantity ≥ 요청 수량)
            if (request.getAmount() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("remainQuantity"), request.getAmount()));
            }

            // 6) 커서 기반 페이징
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
