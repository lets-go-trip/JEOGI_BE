package com.ssafy.tripchat.reservation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.tripchat.common.exception.InvalidRequestException;
import com.ssafy.tripchat.reservation.domain.Reservations;
import com.ssafy.tripchat.reservation.repository.ParkingSpaceReservationRepository;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Service
@Builder
@RequiredArgsConstructor
public class ParkingSpaceReservationServiceImpl implements ParkingSpaceReservationService {

    private final ParkingSpaceReservationRepository parkingSpaceReservationRepository;

    @Override
    public Reservations getReservationById(int reservationId) {
        return parkingSpaceReservationRepository.findReservationById(reservationId)
                .orElseThrow(() -> new InvalidRequestException("예약 내역이 존재하지 않습니다."));
    }

    @Override
    public List<Reservations> getReservationsByMemberId(int memberId) {
        return parkingSpaceReservationRepository.findReservationsByMemberId(memberId);
    }

    @Override
    public void deleteReservation(int memberId, int reservationId) {
        // TODO 실제 존재하는 예약인지 확인
        Reservations reservation = parkingSpaceReservationRepository.findReservationById(reservationId)
                .orElseThrow(() -> new InvalidRequestException("예약 내역이 존재하지 않습니다."));

        // TODO 예약한 본인이 맞는지 확인
        if (reservation.getMember().getId() != memberId) {
            throw new InvalidRequestException("예약한 본인이 아닙니다.");
        }

        // TODO 예약 삭제
        parkingSpaceReservationRepository.deleteReservation(reservationId);
    }
}
