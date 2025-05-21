package com.ssafy.tripchat.reservation.repository;

import com.ssafy.tripchat.reservation.domain.Reservations;
import java.util.List;
import java.util.Optional;

public interface ParkingSpaceReservationRepository {
    List<Reservations> findReservationsByMemberId(int memberId);

    Optional<Reservations> findReservationById(int reservationId);

    void deleteReservation(int reservationId);
}
