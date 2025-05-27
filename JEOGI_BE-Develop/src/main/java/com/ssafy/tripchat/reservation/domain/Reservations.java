package com.ssafy.tripchat.reservation.domain;

import com.ssafy.tripchat.global.domain.BaseEntity;
import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.reservation.dto.request.ParkingReservationRequest;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reservations extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id")
    private ParkingLots parkingLot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members member;

    @Embedded
    private ReservationPeriod reservationPeriod;

    public static Reservations from(ParkingLots parkingLot, ParkingReservationRequest parkingReservationReq,
                                    Members member) {
        return Reservations.builder()
                .parkingLot(parkingLot)
                .member(member)
                .reservationPeriod(new ReservationPeriod(parkingReservationReq.getStartDateTime(),
                        parkingReservationReq.getEndDateTime()))
                .build();
    }
}
