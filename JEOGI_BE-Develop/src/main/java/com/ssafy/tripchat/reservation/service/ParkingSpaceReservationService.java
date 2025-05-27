package com.ssafy.tripchat.reservation.service;

import java.util.List;

import com.ssafy.tripchat.reservation.domain.Reservations;

public interface ParkingSpaceReservationService {
    /**
     * 예약 ID에 해당하는 개인 주차장 예약 내역을 조회합니다.
     *
     * @param reservationId 예약 ID
     * @return 나의 예약 내역 리스트
     */
    Reservations getReservationById(int reservationId);

    /**
     * 회원 ID에 해당하는 개인 주차장 예약 내역 목록을 조회합니다.
     *
     * @param memberId 회원 ID
     * @return 나의 예약 내역 리스트
     */

    List<Reservations> getReservationsByMemberId(int memberId);

    /**
     * 개인 주차장 예약 내역 삭제
     *
     * @param memberId      회원 ID
     * @param reservationId 예약 ID
     */
    void deleteReservation(int memberId, int reservationId);
}
