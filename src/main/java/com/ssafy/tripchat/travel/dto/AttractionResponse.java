package com.ssafy.tripchat.travel.dto;

import com.ssafy.tripchat.reservation.domain.ParkingLots;
import com.ssafy.tripchat.travel.domain.*;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AttractionResponse {

    private int id;
    private int metropolitanCode;
    private int contentTypes;
    private int localCode;
    private String title;
    private List<Images> imgUrl;
    private int parkingLotCount;
    private double latitude;
    private double longitude;
    private String tel;
    private String address;
    //private String overview;

    public AttractionResponse(Attractions attractions) {
        this.metropolitanCode = attractions.getMetropolitan().getCode();
        this.contentTypes = attractions.getContentTypes().getId();
        this.localCode = attractions.getLocal().getId();
        this.title = attractions.getTitle();
        this.imgUrl = attractions.getImgUrl();
        this.parkingLotCount = attractions.getParkingLot().getTotalCount();
        this.latitude = attractions.getLatitude();
        this.longitude = attractions.getLongitude();
        this.tel = attractions.getTel();
        this.address = attractions.getAddress();
        //this.overview = attractions.getOverview();
    }
}
