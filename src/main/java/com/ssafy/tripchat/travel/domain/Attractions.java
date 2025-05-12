package com.ssafy.tripchat.travel.domain;

import com.ssafy.tripchat.reservation.domain.ParkingLots;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "url")
    private List<Images> imgUrl;

    @OneToOne(mappedBy = "attraction", cascade = CascadeType.ALL, orphanRemoval = true)
    private ParkingLots parkingLot;

    private double latitude;
    private double longitude;
    private String tel;
    private String address;
    private String overview;
}
