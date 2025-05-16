package com.ssafy.tripchat.travel.domain;

import org.springframework.data.jpa.domain.Specification;

public class AttractionsSpecification {
	public static Specification<Attractions> filterByConditions(Integer metropolitanCode, Integer localCode, Integer contentTypeId) {
        return Specification.where(hasMetropolitanCode(metropolitanCode))
                .and(hasLocalCode(localCode))
                .and(hasContentTypeId(contentTypeId));
    }
    
    private static Specification<Attractions> hasMetropolitanCode(Integer metropolitanCode) {
        return (root, query, criteriaBuilder) -> {
            if (metropolitanCode == null) {
                return null; // null일 경우 해당 조건은 무시됩니다
            }
            return criteriaBuilder.equal(root.get("metropolitanCode"), metropolitanCode);
        };
    }
    
    private static Specification<Attractions> hasLocalCode(Integer localCode) {
        return (root, query, criteriaBuilder) -> {
            if (localCode == null) {
                return null; // null일 경우 해당 조건은 무시됩니다
            }
            return criteriaBuilder.equal(root.get("localCode"), localCode);
        };
    }
    
    private static Specification<Attractions> hasContentTypeId(Integer contentTypeId) {
        return (root, query, criteriaBuilder) -> {
            if (contentTypeId == null) {
                return null; // null일 경우 해당 조건은 무시됩니다
            }
            return criteriaBuilder.equal(root.get("contentTypeId"), contentTypeId);
        };
    }
}
