package com.ssafy.tripchat.reservation.service;

import com.ssafy.tripchat.reservation.domain.Reservations;
import com.ssafy.tripchat.reservation.repository.ParkingSpaceReservationRepository;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class ParkingSpaceReservationServiceImpl implements ParkingSpaceReservationService {

    private final ParkingSpaceReservationRepository parkingSpaceReservationRepository;

    @Override
    public List<Reservations> getReservationsByMemberId(int memberId) {
        return parkingSpaceReservationRepository.findByReservationsByMemberId(memberId);
    }
}
