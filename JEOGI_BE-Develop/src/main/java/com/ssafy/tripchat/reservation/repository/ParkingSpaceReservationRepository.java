package com.ssafy.tripchat.reservation.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.tripchat.reservation.domain.Reservations;

public interface ParkingSpaceReservationRepository {

    List<Reservations> findReservationsByMemberId(int memberId);

    Optional<Reservations> findReservationById(int reservationId);

    void deleteReservation(int reservationId);
}
