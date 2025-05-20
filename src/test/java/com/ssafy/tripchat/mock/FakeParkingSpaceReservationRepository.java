package com.ssafy.tripchat.mock;

import com.ssafy.tripchat.reservation.domain.Reservations;
import com.ssafy.tripchat.reservation.repository.ParkingSpaceReservationRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class FakeParkingSpaceReservationRepository implements ParkingSpaceReservationRepository {

    private final AtomicInteger id = new AtomicInteger(0);
    private final List<Reservations> data = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<Reservations> findByReservationsByMemberId(int memberId) {
        return data.stream()
                .filter(reservations -> reservations.getMember().getId() == memberId)
                .collect(Collectors.toList());
    }

    public void save(Reservations reservations) {
        if (reservations == null) {
            return;
        }

        Reservations newReservations = Reservations.builder()
                .id(id.incrementAndGet())
                .member(reservations.getMember())
                .parkingLot(reservations.getParkingLot())
                .reservationPeriod(reservations.getReservationPeriod())
                .build();

        data.add(newReservations);
    }
}
