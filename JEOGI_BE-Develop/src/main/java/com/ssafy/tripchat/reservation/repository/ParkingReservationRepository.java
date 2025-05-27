package com.ssafy.tripchat.reservation.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.tripchat.reservation.domain.Reservations;



@Repository
public interface ParkingReservationRepository extends JpaRepository<Reservations, Integer> {

    //요청한 시간대에 예약된 주차 공간 수 조회
	@Query(
		    "SELECT COUNT(r) FROM Reservations r WHERE r.parkingLot.id = :parkingSpaceId " +
		    "AND r.reservationPeriod.startDateTime < :endTime " +
		    "AND r.reservationPeriod.endDateTime > :startTime")
		int countParkingReservationInTimeRange(
		    @Param("parkingSpaceId") int parkingSpaceId,
		    @Param("startTime") LocalDateTime startTime,
		    @Param("endTime") LocalDateTime endTime);

    @Query(
            "SELECT r FROM Reservations r WHERE r.parkingLot.id = :parkingSpaceId " +
                    "AND r.reservationPeriod.startDateTime < :endTime " +
                    "AND r.reservationPeriod.endDateTime > :startTime")
    List<Reservations> findOverlappingReservations(
            @Param("parkingSpaceId") int parkingSpaceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    List<Reservations> findReservationsByMemberId(int memberId);

}
