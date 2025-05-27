package com.ssafy.tripchat.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.mock.FakeParkingSpaceReservationRepository;
import com.ssafy.tripchat.reservation.domain.Reservations;

public class ParkingSpaceReservationServiceTest {

    private ParkingSpaceReservationServiceImpl parkingSpaceReservationService;

    @BeforeEach
    void init() {
        FakeParkingSpaceReservationRepository fakeParkingSpaceReservationRepository = new FakeParkingSpaceReservationRepository();

        parkingSpaceReservationService = ParkingSpaceReservationServiceImpl.builder()
                .parkingSpaceReservationRepository(fakeParkingSpaceReservationRepository)
                .build();

        Members member = Members.builder()
                .id(1)
                .username("testUser")
                .password("testPassword")
                .email("test@test.com")
                .nickname("testNickname")
                .build();

        fakeParkingSpaceReservationRepository.save(Reservations.builder()
                .parkingLot(null)
                .reservationPeriod(null)
                .member(member)
                .build()
        );

        fakeParkingSpaceReservationRepository.save(Reservations.builder()
                .parkingLot(null)
                .reservationPeriod(null)
                .member(member)
                .build()
        );
    }


    @DisplayName("내가 예약한 주차공간 조회")
    @Test
    public void getMyReservationHistory() throws Exception {
        // given
        Members member = Members.builder()
                .id(2)
                .username("testUser2")
                .password("testPassword")
                .email("test2@test.com")
                .nickname("testNickname2")
                .build();

        // when
        List<Reservations> myReservations = parkingSpaceReservationService.getReservationsByMemberId(1);

        // then
        assertThat(myReservations).hasSize(2);
    }
}
