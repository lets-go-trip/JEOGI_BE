package com.ssafy.tripchat.travel.dto;

import com.ssafy.tripchat.travel.domain.Attractions;

import lombok.Getter;

@Getter
public class AttractionDetailResponse {

    private int id;
    private int metropolitanCode;
    private int contentTypes;
    private int localCode;
    private String title;
    private String imgUrl;
    private int parkingLotCount;
    private double latitude;
    private double longitude;
    private String tel;
    private String address;
    private String overview;

    public AttractionDetailResponse(Attractions attractions) {
        this.id = attractions.getId();
        this.metropolitanCode = attractions.getMetropolitan().getCode();
        this.contentTypes = attractions.getContentTypes().getId();
        this.localCode = attractions.getLocal().getId();
        this.title = attractions.getTitle();
        this.imgUrl = attractions.getUrl();
        this.parkingLotCount = attractions.getParkingLot() == null ? 0 : attractions.getParkingLot().getTotalCount();
        this.latitude = attractions.getLatitude();
        this.longitude = attractions.getLongitude();
        this.tel = attractions.getTel();
        this.address = attractions.getAddress();
        this.overview = attractions.getOverview();
    }

//    private String extractImage(List<Images> img) {
//        if (img == null || img.isEmpty()) {
//            return null;
//        }
//        return img.get(0).getUrl();
//    }
}
