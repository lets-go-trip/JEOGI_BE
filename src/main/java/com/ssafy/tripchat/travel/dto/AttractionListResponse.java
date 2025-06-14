package com.ssafy.tripchat.travel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttractionListResponse {

    String message = "검색 성공";
    int length;
    double fetchTime;
    List<AttractionResponse> attractions;

    public AttractionListResponse(List<AttractionResponse> attractions, double fetchTime) {
        this.attractions = attractions;
        this.fetchTime = fetchTime;
        this.length = attractions.size();
    }
}
