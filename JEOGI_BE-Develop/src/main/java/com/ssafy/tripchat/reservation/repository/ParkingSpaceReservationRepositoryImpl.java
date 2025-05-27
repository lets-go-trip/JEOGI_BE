package com.ssafy.tripchat.reservation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ssafy.tripchat.reservation.domain.Reservations;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ParkingSpaceReservationRepositoryImpl implements ParkingSpaceReservationRepository {

    private final ParkingReservationRepository parkingReservationRepository;

    @Override
    public List<Reservations> findReservationsByMemberId(int memberId) {
        return parkingReservationRepository.findReservationsByMemberId(memberId);
    }

    @Override
    public Optional<Reservations> findReservationById(int reservationId) {
        return parkingReservationRepository.findById(reservationId);
    }

    @Override
    public void deleteReservation(int reservationId) {
        parkingReservationRepository.deleteById(reservationId);
    }
}
