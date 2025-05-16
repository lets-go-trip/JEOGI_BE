package com.ssafy.tripchat.travel.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttractionSearchCondition {
	
	// 기존 지역별, 유형별 필터링 
	// regionCode = metropolitanCode * 1000 + localCode
	@Min(value = 1000)
	private int regionCode;
	
	private int metropolitanCode;
	private int localCode;
	private int contentTypeId;
	
	private boolean isRangeSearch;
	
	// 주변 검색
	@Min(value = 33)
	@Max(value = 43)
	private double latitude;
	
	@Min(value = 123)
	@Max(value = 133)
	private double longitude;
	
	@Min(value = 0)
	@Max(value = 50)
	private double range;
}
