package com.ssafy.tripchat.reservation.service;

import com.ssafy.tripchat.reservation.domain.Reservations;
import java.util.List;

public interface ParkingSpaceReservationService {

    List<Reservations> getReservationsByMemberId(int memberId);
}
