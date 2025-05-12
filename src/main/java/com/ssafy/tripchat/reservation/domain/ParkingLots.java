package com.ssafy.tripchat.reservation.domain;

import com.ssafy.tripchat.travel.domain.Attractions;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ParkingLots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "attractions_id", nullable = false, unique = true)
    private Attractions attraction;

    private int totalCount;
    private int availableCount;
}
