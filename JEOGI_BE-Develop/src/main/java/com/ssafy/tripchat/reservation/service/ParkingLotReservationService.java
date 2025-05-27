package com.ssafy.tripchat.reservation.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.tripchat.common.aop.WithLock;
import com.ssafy.tripchat.common.exception.InvalidRequestException;
import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.member.domain.MembersRepository;
import com.ssafy.tripchat.reservation.domain.ParkingLots;
import com.ssafy.tripchat.reservation.domain.Reservations;
import com.ssafy.tripchat.reservation.dto.request.ParkingReservationRequest;
import com.ssafy.tripchat.reservation.dto.response.ParkingReservationResponse;
import com.ssafy.tripchat.reservation.repository.ParkingLotsRepository;
import com.ssafy.tripchat.reservation.repository.ParkingReservationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingLotReservationService {

    private final ParkingLotsRepository parkingLotsRepository;
    private final ParkingReservationRepository parkingReservationRepository;
    private final MembersRepository membersRepository;

    /**
     * 남은 주차장 공간 자리 수 조회
     *
     * @param reservationRequest 주차장 ID, 예약 시작일시, 예약 종료일시
     * @return 남은 주차 공간 자리 수
     * @Description 주차장 ID를 통해 주차장 정보를 조회하고, 해당 주차장의 남은 주차 공간 자리 수를 반환합니다.
     */

    @Transactional(readOnly = true)
    public int getAvailableParkingSpaces(int parkingLotId, ParkingReservationRequest reservationRequest) {

        ParkingLots parkingLot = parkingLotsRepository.findById(parkingLotId)
                .orElseThrow(() -> new InvalidRequestException("존재하지 않는 주차장입니다."));
        int reservationCount = parkingReservationRepository.countParkingReservationInTimeRange(parkingLotId,
                reservationRequest.getStartDateTime(), reservationRequest.getEndDateTime());
        // 요청한 시간에 사용 가능한 주차 공간 수
        return parkingLot.getTotalCount() - reservationCount;
    }

    /**
     * 주차장 예약
     *
     * @param reservationRequest 주차장 예약 요청 정보
     * @return 예약 성공 여부
     */
    @WithLock(key = "#parkingLotId")
    @Transactional
    public ParkingReservationResponse reserveParkingLot(int parkingLotId, int memberId,
                                                        ParkingReservationRequest reservationRequest) {

        ParkingLots parkingLot = parkingLotsRepository.findById(parkingLotId)
                .orElseThrow(() -> new InvalidRequestException("존재하지 않는 주차장입니다."));

        Members member = membersRepository.findById(memberId)
                .orElseThrow(() -> new InvalidRequestException("존재하지 않는 회원입니다."));

        LocalDateTime reservationRequestStartDateTime = reservationRequest.getStartDateTime();
        LocalDateTime reservationRequestEndDateTime = reservationRequest.getEndDateTime();

        List<Reservations> overlaps = parkingReservationRepository.findOverlappingReservations(
                parkingLotId,
                reservationRequestStartDateTime,
                reservationRequestEndDateTime);

        checkDuplicateRequest(memberId, overlaps);

        int availableParkingSpaces = parkingLot.getTotalCount() - overlaps.size();

        if (availableParkingSpaces <= 0) {
            throw new InvalidRequestException("예약 가능한 주차 공간이 없습니다.");
        }

        // 주차장 예약
        Reservations reservations = parkingReservationRepository.save(
                Reservations.from(parkingLot, reservationRequest, member));

        return ParkingReservationResponse.from(reservations);
    }

    private void checkDuplicateRequest(int memberId, List<Reservations> overlaps) {
        boolean hasDuplicationForSameMember = overlaps.stream()
                .anyMatch(reservation -> reservation.getMember().getId() == memberId);

        if (hasDuplicationForSameMember) {
            throw new InvalidRequestException("이미 동일 시간대 예약이 존재합니다.");
        }
    }


}
