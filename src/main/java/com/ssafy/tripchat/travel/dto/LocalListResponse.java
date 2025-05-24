package com.ssafy.tripchat.travel.dto;

import com.ssafy.tripchat.travel.domain.Locals;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LocalListResponse {
    String message = "기초단체 검색 성공";
    int length;
    List<Locals> locals;

    public LocalListResponse(List<Locals> localsList) {
        this.locals = localsList;
        this.length = locals.size();
    }
}
