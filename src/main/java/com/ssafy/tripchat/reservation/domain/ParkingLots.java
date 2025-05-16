package com.ssafy.tripchat.reservation.domain;

import com.ssafy.tripchat.travel.domain.Attractions;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParkingLots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "attractions_id", nullable = false, unique = true)
    private Attractions attraction;

    private int totalCount;

    @Builder
    public ParkingLots(int id, Attractions attraction, int totalCount) {
        this.id = id;
        this.attraction = attraction;
        this.totalCount = totalCount;
    }
}
