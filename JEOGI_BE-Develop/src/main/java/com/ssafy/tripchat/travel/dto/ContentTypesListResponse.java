package com.ssafy.tripchat.travel.dto;

import java.util.List;

import com.ssafy.tripchat.travel.domain.ContentTypes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ContentTypesListResponse {
        String message = "여행지 유형 검색 성공";
        int length;
        List<ContentTypes> contentTypes;

        public ContentTypesListResponse(List<ContentTypes> contentTypes) {
            this.contentTypes = contentTypes;
            this.length = contentTypes.size();
        }
}
