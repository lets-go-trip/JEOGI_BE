package com.ssafy.tripchat.travel;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.tripchat.travel.domain.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.travel.dto.AttractionSearchCondition;
import com.ssafy.tripchat.travel.repository.AttractionsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttractionService {

	private static final double KM_TO_LATITUDE = 0.009;
	private static final double KM_TO_LONGITUDE = 0.011;

	private final AttractionsRepository attractionsRepository;
	private final JPAQueryFactory queryFactory;

	QAttractions attractions = QAttractions.attractions;
	QMetropolitans metropolitans = QMetropolitans.metropolitans;
	QLocals locals = QLocals.locals;
	QContentTypes contentTypes = QContentTypes.contentTypes;
	
    public List<Attractions> searchByCondition(AttractionSearchCondition searchCondition) {
    	int regionCode = searchCondition.getRegionCode();
    	searchCondition.setMetropolitanCode(regionCode / 1000);
    	searchCondition.setLocalCode(regionCode % 1000);

		List<Attractions> result = queryFactory
				.select(attractions)
				.from(attractions)
				.where(
						eqMetropolitanCode(searchCondition.getMetropolitanCode()),
						eqLocalCode(searchCondition.getLocalCode()),
						eqContentTypes(searchCondition.getContentTypeId()),
						betweenLatitude(searchCondition.isRangeSearch(), searchCondition.getLatitude(), searchCondition.getRange() * KM_TO_LATITUDE),
						betweenLongitude(searchCondition.isRangeSearch(), searchCondition.getLongitude(), searchCondition.getRange() * KM_TO_LONGITUDE)
				)
				.fetch();

		if (!searchCondition.isRangeSearch()){
			return result;
		}

		List<Attractions> resultInRange = new ArrayList<>();
		for (Attractions attraction : result) {
			if (isInRange(searchCondition, attraction)) {
				resultInRange.add(attraction);
			}
		}

		return resultInRange;
    }

	private BooleanExpression eqMetropolitanCode(Integer metropolitanCode) {
		return metropolitanCode != null ? attractions.metropolitan.code.eq(metropolitanCode) : null;
	}

	private BooleanExpression eqLocalCode(Integer localCode) {
		return localCode != null ? attractions.local.code.eq(localCode) : null;
	}

	private BooleanExpression eqContentTypes(Integer contentTypes) {
		return contentTypes != null ? attractions.contentTypes.id.eq(contentTypes) : null;
	}

	private BooleanExpression betweenLatitude(Boolean isRangeSearch, Double latitude, Double range){
		return isRangeSearch ? attractions.latitude.between(latitude - range, latitude + range) : null;
	}

	private BooleanExpression betweenLongitude(Boolean isRangeSearch, Double longitude, Double range){
		return isRangeSearch ? attractions.longitude.between(longitude - range, longitude + range) : null;
	}

	private static boolean isInRange(AttractionSearchCondition searchCondition, Attractions target) {
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
