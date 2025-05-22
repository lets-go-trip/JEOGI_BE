package com.ssafy.tripchat.reservation.dto.response;

import com.ssafy.tripchat.reservation.domain.Reservations;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParkingReservationsResponse {
    private List<ParkingReservationResponse> responseReservations;

    public static ParkingReservationsResponse from(List<Reservations> reservations) {
        List<ParkingReservationResponse> responses = reservations.stream()
                .map(ParkingReservationResponse::from)
                .collect(Collectors.toList());

        return ParkingReservationsResponse.builder()
                .responseReservations(responses)
                .build();
    }
}
