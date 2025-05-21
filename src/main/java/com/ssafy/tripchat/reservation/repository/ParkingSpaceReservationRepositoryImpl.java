package com.ssafy.tripchat.reservation.repository;

import com.ssafy.tripchat.reservation.domain.Reservations;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
