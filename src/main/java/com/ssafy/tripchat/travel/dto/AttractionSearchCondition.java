package com.ssafy.tripchat.travel.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AttractionSearchCondition {
	
	// 기존 지역별, 유형별 필터링 
	// regionCode = metropolitanCode * 1000 + localCode
	private Integer regionCode;
	
	private int metropolitanCode;
	private int localCode;
	private int contentTypeId;
	
	private Boolean isRangeSearch;

	private Double latitude;

	private Double longitude;

	private Double range;
}
