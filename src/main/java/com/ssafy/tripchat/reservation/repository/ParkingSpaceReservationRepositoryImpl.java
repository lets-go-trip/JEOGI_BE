package com.ssafy.tripchat.reservation.repository;

import com.ssafy.tripchat.reservation.domain.Reservations;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParkingSpaceReservationRepositoryImpl implements ParkingSpaceReservationRepository {

    private final ParkingReservationRepository parkingReservationRepository;

    @Override
    public List<Reservations> findByReservationsByMemberId(int memberId) {
        return parkingReservationRepository.findReservationsByMemberId(memberId);
    }
}
