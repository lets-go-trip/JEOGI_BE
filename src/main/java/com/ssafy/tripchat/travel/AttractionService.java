package com.ssafy.tripchat.travel;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tripchat.travel.domain.Attractions;
import com.ssafy.tripchat.travel.domain.QAttractions;
import com.ssafy.tripchat.travel.dto.AttractionListResponse;
import com.ssafy.tripchat.travel.dto.AttractionResponse;
import com.ssafy.tripchat.travel.dto.AttractionSearchCondition;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttractionService {

    private static final double KM_TO_LATITUDE = 0.009;
    private static final double KM_TO_LONGITUDE = 0.011;

    // private final AttractionsRepository attractionsRepository;
    private final JPAQueryFactory queryFactory;

    QAttractions attractions = QAttractions.attractions;

    public AttractionListResponse searchByCondition(AttractionSearchCondition searchCondition) {
        long startTime = System.currentTimeMillis();
        int regionCode = searchCondition.getRegionCode() == null ? 0 : searchCondition.getRegionCode();
        searchCondition.setMetropolitanCode(regionCode / 1000);
        searchCondition.setLocalCode(regionCode % 1000);

        List<Attractions> result = queryFactory
                .select(attractions)
                .from(attractions)
                .where(
                        eqMetropolitanCode(searchCondition.getMetropolitanCode()),
                        eqLocalCode(searchCondition.getLocalCode()),
                        eqContentTypes(searchCondition.getContentTypeId()),
                        betweenLatitude(searchCondition.getIsRangeSearch(), searchCondition.getLatitude(),
                                searchCondition.getRange()),
                        betweenLongitude(searchCondition.getIsRangeSearch(), searchCondition.getLongitude(),
                                searchCondition.getRange())
                )
                .fetch();

        List<AttractionResponse> response = new ArrayList<>();
        for (Attractions attraction : result) {
            response.add(new AttractionResponse(attraction));
        }

        if (searchCondition.getIsRangeSearch() == null || !searchCondition.getIsRangeSearch()) {
            double fetchTime = (System.currentTimeMillis() - startTime) / 1000.0;
            return new AttractionListResponse(response, fetchTime);
        }

        List<AttractionResponse> responseInRange = new ArrayList<>();
        for (AttractionResponse attraction : response) {
            if (isInRange(searchCondition, attraction)) {
                responseInRange.add(attraction);
            }
        }

        double fetchTime = (System.currentTimeMillis() - startTime) / 1000.0;
        return new AttractionListResponse(responseInRange, fetchTime);
    }

    private BooleanExpression eqMetropolitanCode(Integer metropolitanCode) {
        return metropolitanCode != 0 ? attractions.metropolitan.code.eq(metropolitanCode) : null;
    }

    private BooleanExpression eqLocalCode(Integer localCode) {
        return localCode != 0 ? attractions.local.code.eq(localCode) : null;
    }

    private BooleanExpression eqContentTypes(Integer contentTypes) {
        return contentTypes != 0 ? attractions.contentTypes.id.eq(contentTypes) : null;
    }

    private BooleanExpression betweenLatitude(Boolean isRangeSearch, Double latitude, Double range) {
        if (range == null) {
            return null;
        }
        return isRangeSearch != null ? attractions.latitude.between(latitude - range, latitude + range) : null;
    }

    private BooleanExpression betweenLongitude(Boolean isRangeSearch, Double longitude, Double range) {
        if (range == null) {
            return null;
        }
        return isRangeSearch != null ? attractions.longitude.between(longitude - range, longitude + range) : null;
    }

    private static boolean isInRange(AttractionSearchCondition searchCondition, AttractionResponse target) {
        double distance;
        double radius = 6371; // 지구 반지름(km)
        double toRadian = Math.PI / 180;

        double x1 = searchCondition.getLatitude();
        double y1 = searchCondition.getLongitude();
        double x2 = target.getLatitude();
        double y2 = target.getLongitude();

        double deltaLatitude = Math.abs(x1 - x2) * toRadian;
        double deltaLongitude = Math.abs(y1 - y2) * toRadian;

        double sinDeltaLat = Math.sin(deltaLatitude / 2);
        double sinDeltaLng = Math.sin(deltaLongitude / 2);
        double squareRoot = Math.sqrt(
                sinDeltaLat * sinDeltaLat +
                        Math.cos(x1 * toRadian) * Math.cos(x2 * toRadian) * sinDeltaLng * sinDeltaLng);

        distance = 2 * radius * Math.asin(squareRoot);

        return distance <= searchCondition.getRange();
    }
}
