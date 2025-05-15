package com.ssafy.tripchat.reservation.dto.response;

import com.ssafy.tripchat.reservation.domain.ReservationPeriod;
import com.ssafy.tripchat.reservation.domain.Reservations;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkingReservationResponse {
    private int parkingLotId;
    private String username;
    private ReservationPeriod reservationPeriod;


    public static ParkingReservationResponse from(Reservations reservations) {
        return ParkingReservationResponse.builder()
                .parkingLotId(reservations.getParkingLot().getId())
                .username(reservations.getMember().getUsername())
                .reservationPeriod(reservations.getReservationPeriod())
                .build();
    }
}
