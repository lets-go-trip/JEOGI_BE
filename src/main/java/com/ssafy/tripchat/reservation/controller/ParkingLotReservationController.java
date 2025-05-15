package com.ssafy.tripchat.reservation.controller;

import com.ssafy.tripchat.global.security.domain.MemberPrincipal;
import com.ssafy.tripchat.reservation.dto.request.ParkingReservationRequest;
import com.ssafy.tripchat.reservation.dto.response.ParkingReservationResponse;
import com.ssafy.tripchat.reservation.service.ParkingLotReservationService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParkingLotReservationController {

    private final ParkingLotReservationService parkingLotReservationService;

    @GetMapping("/api/v1/parking-reservations/{parkingLotId}")
    public int getAvailableParkingSpaces(
            @PathVariable int parkingLotId,
            @ParameterObject ParkingReservationRequest request) {
        return parkingLotReservationService.getAvailableParkingSpaces(request);
    }
    
    @PostMapping("/api/v1/parking-reservations/{parkingLotId}")
    public ResponseEntity<ParkingReservationResponse> createParkingReservation(
            @AuthenticationPrincipal MemberPrincipal principal,
            @PathVariable int parkingLotId,
            @ParameterObject ParkingReservationRequest request) {
        int memberId = principal.getId();

        URI location = URI.create("/api/v1/parking-reservations/" + parkingLotId);
        return ResponseEntity.created(location).body(parkingLotReservationService.reserveParkingLot(memberId, request));
    }
}
