package com.ssafy.tripchat.reservation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.ssafy.tripchat.common.exception.InvalidRequestException;
import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.member.domain.MembersRepository;
import com.ssafy.tripchat.reservation.domain.ParkingLots;
import com.ssafy.tripchat.reservation.domain.Reservations;
import com.ssafy.tripchat.reservation.dto.request.ParkingReservationRequest;
import com.ssafy.tripchat.reservation.dto.response.ParkingReservationResponse;
import com.ssafy.tripchat.reservation.repository.ParkingLotsRepository;
import com.ssafy.tripchat.reservation.repository.ParkingReservationRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ParkingLotReservationServiceTest {

    @Mock
    private ParkingLotsRepository parkingLotsRepository;

    @Mock
    private ParkingReservationRepository parkingReservationRepository;

    @Mock
    private MembersRepository membersRepository;

    @InjectMocks
    private ParkingLotReservationService parkingService;

    @DisplayName("지정한 시간 날짜에 사용 가능한 주차 공간 수 조회")
    @Test
    public void getAvailableParkingSpacesByDate() throws Exception {
        // given
        int parkingLotId = 1;
        int totalSpaces = 10;
        int reservedSpaces = 3;
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(2L);

        ParkingReservationRequest request = createParkingReservationRequest(1, startTime, endTime);
        ParkingLots parkingLot = createParkingLot(parkingLotId, totalSpaces);

        when(parkingLotsRepository.findById(parkingLotId)).thenReturn(Optional.of(parkingLot));
        when(parkingReservationRepository.countParkingReservationInTimeRange(parkingLotId, startTime, endTime))
                .thenReturn(reservedSpaces);

        // when
        int result = parkingService.getAvailableParkingSpaces(request);

        // then
        int expectedAvailableSpaces = totalSpaces - reservedSpaces;

        assertThat(result).isEqualTo(expectedAvailableSpaces);
    }

    @DisplayName("존재하지 않는 주차장의 주차공간 조회시 예외 발생")
    @Test
    public void getNotExistAvailableParkingSpaces() throws Exception {
        // given
        int inValidParkingLotId = 2;
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(2L);

        ParkingReservationRequest parkingReservationRequest = createParkingReservationRequest(inValidParkingLotId,
                startTime,
                endTime);

        when(parkingLotsRepository.findById(inValidParkingLotId)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> parkingService.getAvailableParkingSpaces(parkingReservationRequest))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("존재하지 않는 주차장입니다.");
    }

    @DisplayName("주차 예약 가능한 경우 성공적으로 예약할 수 있다.")
    @Test
    public void canReserveParkingSpace() throws Exception {
        // given
        ParkingLots parkingLot = createParkingLot(1, 10);
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(2L);

        int memberId = 1;

        Members member = Members.builder()
                .id(memberId)
                .email("test@gmail.com")
                .username("홍길동")
                .build();

        ParkingReservationRequest parkingReservationRequest = createParkingReservationRequest(1, startTime, endTime);

        when(membersRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(parkingLotsRepository.findById(1)).thenReturn(Optional.of(parkingLot));
        when(parkingReservationRepository.countParkingReservationInTimeRange(1, startTime, endTime)).thenReturn(1);

        Reservations dummy = Reservations.from(parkingLot, parkingReservationRequest, member);
        when(parkingReservationRepository.save(Mockito.any(Reservations.class)))
                .thenReturn(dummy);

        // when
        ParkingReservationResponse parkingReservationResponse = parkingService.reserveParkingLot(memberId,
                parkingReservationRequest);

        //then
        assertThat(parkingReservationResponse).extracting(
                        ParkingReservationResponse::getParkingLotId,
                        ParkingReservationResponse::getUsername,
                        r -> r.getReservationPeriod().getStartDateTime(),
                        r -> r.getReservationPeriod().getEndDateTime())
                .contains(1, "홍길동", startTime, endTime);
    }

    @DisplayName("주차 공간이 부족한 경우 예약할 수 없다.")
    @Test
    public void canNotReserveParkingSpace() throws Exception {
        // given
        ParkingLots parkingLot = createParkingLot(1, 10);
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(2L);

        int memberId = 1;

        Members member = Members.builder()
                .id(memberId)
                .email("test@gmail.com")
                .build();

        ParkingReservationRequest parkingReservationRequest = createParkingReservationRequest(1, startTime, endTime);

        when(membersRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(parkingLotsRepository.findById(1)).thenReturn(Optional.of(parkingLot));
        when(parkingReservationRepository.countParkingReservationInTimeRange(1, startTime, endTime)).thenReturn(10);

        // when // then
        assertThatThrownBy(() -> parkingService.reserveParkingLot(memberId, parkingReservationRequest))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("예약 가능한 주차 공간이 없습니다.");
    }

    private ParkingLots createParkingLot(int parkingLotId, int totalSpaces) {
        return ParkingLots.builder()
                .id(parkingLotId)
                .totalCount(totalSpaces)
                .build();
    }

    private ParkingReservationRequest createParkingReservationRequest(int parkingLotId, LocalDateTime startDateTime,
                                                                      LocalDateTime endDateTime) {
        return ParkingReservationRequest.builder()
                .parkingLotId(parkingLotId)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}