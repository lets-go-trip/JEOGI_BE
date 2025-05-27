package com.ssafy.tripchat.travel.domain;

import com.ssafy.tripchat.reservation.domain.ParkingLots;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Attractions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "metropolitans_id")
    private Metropolitans metropolitan;

    @ManyToOne
    @JoinColumn(name = "content_types_id")
    private ContentTypes contentTypes;

    @ManyToOne
    @JoinColumn(name = "locals_id")
    private Locals local;

    private String title;

    //    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private String url;

    @OneToOne(mappedBy = "attraction", cascade = CascadeType.ALL, orphanRemoval = true)
    private ParkingLots parkingLot;

    private double latitude;
    private double longitude;
    private String tel;
    private String address;
    
    @Column(length = 10000)
    private String overview;

    public Attractions(Metropolitans metropolitan, ContentTypes contentTypes, Locals local, String title,
                       String imgUrl, ParkingLots parkingLot, double latitude, double longitude, String tel,
                       String address, String overview) {
        this.metropolitan = metropolitan;
        this.contentTypes = contentTypes;
        this.local = local;
        this.title = title;
        this.url = imgUrl;
        this.parkingLot = parkingLot;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tel = tel;
        this.address = address;
        this.overview = overview;
    }
}
