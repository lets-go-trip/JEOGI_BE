package com.ssafy.tripchat.reservation.dto.response;

import com.ssafy.tripchat.reservation.domain.ReservationPeriod;
import com.ssafy.tripchat.reservation.domain.Reservations;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkingReservationResponse {
    private int reservationId;
    private int parkingLotId;
    private String username;
    private ReservationPeriod reservationPeriod;
    private LocalDateTime createdAt;
    private String attractionName;


    public static ParkingReservationResponse from(Reservations reservations) {
        return ParkingReservationResponse.builder()
                .reservationId(reservations.getId())
                .attractionName(reservations.getParkingLot().getAttraction().getTitle())
                .parkingLotId(reservations.getParkingLot().getId())
                .username(reservations.getMember().getUsername())
                .reservationPeriod(reservations.getReservationPeriod())
                .createdAt(reservations.getCreatedAt())
                .build();
    }

    public ParkingReservationResponse(
            int reservationId,
            int parkingLotId,
            String username,
            ReservationPeriod reservationPeriod,
            LocalDateTime createdAt,
            String attractionName
    ) {
        this.reservationId = reservationId;
        this.parkingLotId = parkingLotId;
        this.username = username;
        this.reservationPeriod = reservationPeriod;
        this.createdAt = createdAt;
        this.attractionName = attractionName;
    }
}
