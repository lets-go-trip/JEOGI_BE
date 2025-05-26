package com.ssafy.tripchat.reservation.controller;

import com.ssafy.tripchat.global.security.domain.MemberPrincipal;
import com.ssafy.tripchat.reservation.dto.request.ParkingReservationRequest;
import com.ssafy.tripchat.reservation.dto.response.ParkingReservationResponse;
import com.ssafy.tripchat.reservation.dto.response.ParkingReservationsResponse;
import com.ssafy.tripchat.reservation.service.ParkingLotReservationService;
import com.ssafy.tripchat.reservation.service.ParkingSpaceReservationService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParkingLotReservationController {

    private final ParkingLotReservationService parkingLotReservationService;
    private final ParkingSpaceReservationService parkingSpaceReservationService;

    @GetMapping("/api/v1/parking-reservations/{parkingLotId}")
    public int getAvailableParkingSpaces(
            @PathVariable int parkingLotId,
            @ParameterObject ParkingReservationRequest request) {
        return parkingLotReservationService.getAvailableParkingSpaces(parkingLotId, request);
    }

    @PostMapping("/api/v1/parking-lots/{parkingLotId}/reservation")
    public ResponseEntity<ParkingReservationResponse> createParkingReservation(
            @AuthenticationPrincipal MemberPrincipal principal,
            @PathVariable Integer parkingLotId,
            @Valid @RequestBody ParkingReservationRequest request) {
        int memberId = principal.getId();

        ParkingReservationResponse response = parkingLotReservationService.reserveParkingLot(parkingLotId, memberId,
                request);
        URI location = URI.create("/api/v1/parking-reservations/me");

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/api/v1/parking-reservations/me")
    public ResponseEntity<ParkingReservationsResponse> getMyParkingReservations(
            @AuthenticationPrincipal MemberPrincipal principal) {
        int memberId = principal.getId();
        return ResponseEntity.ok(
                ParkingReservationsResponse.from(parkingSpaceReservationService.getReservationsByMemberId(memberId)));
    }

    @DeleteMapping("/api/v1/parking-reservations/{reservationId}")
    public ResponseEntity<Void> deleteParkingReservation(
            @AuthenticationPrincipal MemberPrincipal principal,
            @PathVariable int reservationId) {
        int memberId = principal.getId();
        parkingSpaceReservationService.deleteReservation(memberId, reservationId);
        return ResponseEntity.noContent().build();
    }
}
