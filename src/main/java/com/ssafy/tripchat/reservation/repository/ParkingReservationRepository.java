package com.ssafy.tripchat.reservation.repository;

import com.ssafy.tripchat.reservation.domain.Reservations;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingReservationRepository extends JpaRepository<Reservations, Integer> {

    //요청한 시간대에 예약된 주차 공간 수 조회
    @Query(
            "SELECT COUNT(r) FROM Reservations r WHERE r.parkingLot.id = :parkingSpaceId " +
                    "AND r.reservationPeriod.startDateTime < :endTime " +
                    "AND r.reservationPeriod.endDateTime > :startTime")
    int countParkingReservationInTimeRange(
            int parkingSpaceId,
            LocalDateTime startTime,
            LocalDateTime endTime);

}
