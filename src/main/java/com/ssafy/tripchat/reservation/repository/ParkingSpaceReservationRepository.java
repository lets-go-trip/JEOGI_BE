package com.ssafy.tripchat.reservation.repository;

import com.ssafy.tripchat.reservation.domain.Reservations;
import java.util.List;

public interface ParkingSpaceReservationRepository {

    List<Reservations> findByReservationsByMemberId(int memberId);
}
