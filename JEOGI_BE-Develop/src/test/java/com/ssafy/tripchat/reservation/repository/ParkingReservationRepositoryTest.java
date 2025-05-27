package com.ssafy.tripchat.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.member.domain.MembersRepository;
import com.ssafy.tripchat.reservation.domain.ParkingLots;
import com.ssafy.tripchat.reservation.domain.ReservationPeriod;
import com.ssafy.tripchat.reservation.domain.Reservations;
import com.ssafy.tripchat.travel.domain.Attractions;
import com.ssafy.tripchat.travel.domain.ContentTypes;
import com.ssafy.tripchat.travel.domain.Locals;
import com.ssafy.tripchat.travel.domain.Metropolitans;
import com.ssafy.tripchat.travel.repository.AttractionsRepository;
import com.ssafy.tripchat.travel.repository.ContentTypesRepository;
import com.ssafy.tripchat.travel.repository.LocalsRepository;
import com.ssafy.tripchat.travel.repository.MetropolitansRepository;

@DataJpaTest
@ActiveProfiles("test")
class ParkingReservationRepositoryTest {

    @Autowired
    private ParkingReservationRepository parkingReservationRepository;
    @Autowired
    private ParkingLotsRepository parkingLotsRepository;
    @Autowired
    private AttractionsRepository attractionsRepository;
    @Autowired
    private ContentTypesRepository contentTypesRepository;
    @Autowired
    private LocalsRepository localsRepository;
    @Autowired
    private MetropolitansRepository metropolitansRepository;
    @Autowired
    private MembersRepository membersRepository;

    @AfterEach
    public void tearDown() {
        parkingReservationRepository.deleteAllInBatch();
        parkingLotsRepository.deleteAllInBatch();
        attractionsRepository.deleteAllInBatch();
        contentTypesRepository.deleteAllInBatch();
        localsRepository.deleteAllInBatch();
        metropolitansRepository.deleteAllInBatch();
        membersRepository.deleteAllInBatch();
    }

    // 주차 공간 조회, 주차 예약 생성
    @DisplayName("잔여 주차 공간 조회 테스트")
    @Test
    public void countAvailableParkingLot() throws Exception {
        // given
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(1L);

        Members member = createMember("1");
        membersRepository.save(member);

        ContentTypes contentsType = createContentType("관광지");
        Metropolitans metropolitan = createMetropolitan(1, "서울특별시");
        Locals local = createLocal(metropolitan.getCode(), 1, "노원구");

        Attractions attraction = new Attractions(metropolitan, contentsType, local, "제목", null, null, 0, 0,
                null, null, null);
        attractionsRepository.save(attraction);

        ParkingLots parkingLot = ParkingLots.builder()
                .attraction(attraction)
                .totalCount(10)
                .build();
        parkingLotsRepository.save(parkingLot);

        ReservationPeriod reservationPeriod1 = ReservationPeriod.builder()
                .startDateTime(startTime)
                .endDateTime(endTime).build();

        Reservations reservations1 = Reservations.builder()
                .reservationPeriod(reservationPeriod1)
                .member(member)
                .parkingLot(parkingLot)
                .build();

        parkingReservationRepository.save(reservations1);

        LocalDateTime startTime2 = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime2 = startTime2.plusHours(2L);

        ReservationPeriod reservationPeriod2 = ReservationPeriod.builder()
                .startDateTime(startTime2)
                .endDateTime(endTime2).build();

        Reservations reservations2 = Reservations.builder()
                .reservationPeriod(reservationPeriod2)
                .member(member)
                .parkingLot(parkingLot)
                .build();

        parkingReservationRepository.save(reservations2);

        // when
        int reservationCount = parkingReservationRepository.countParkingReservationInTimeRange(parkingLot.getId(),
                startTime, endTime);

        // then
        assertThat(reservationCount).isEqualTo(2);
    }

    @DisplayName("지정시간에 예약된 정보를 조회한다")
    @Test
    public void getReservedParkingLotsInfo() throws Exception {
        // given
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(1L);

        Members member = createMember("1");
        membersRepository.save(member);

        ContentTypes contentsType = createContentType("관광지");
        Metropolitans metropolitan = createMetropolitan(1, "서울특별시");
        Locals local = createLocal(metropolitan.getCode(), 1, "노원구");

        Attractions attraction = new Attractions(metropolitan, contentsType, local, "제목", null, null, 0, 0,
                null, null, null);
        attractionsRepository.save(attraction);

        ParkingLots parkingLot = ParkingLots.builder()
                .attraction(attraction)
                .totalCount(10)
                .build();
        parkingLotsRepository.save(parkingLot);

        ReservationPeriod reservationPeriod1 = ReservationPeriod.builder()
                .startDateTime(startTime)
                .endDateTime(endTime).build();

        Reservations reservations1 = Reservations.builder()
                .reservationPeriod(reservationPeriod1)
                .member(member)
                .parkingLot(parkingLot)
                .build();

        parkingReservationRepository.save(reservations1);

        LocalDateTime startTime2 = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime2 = startTime2.plusHours(2L);

        ReservationPeriod reservationPeriod2 = ReservationPeriod.builder()
                .startDateTime(startTime2)
                .endDateTime(endTime2).build();

        Reservations reservations2 = Reservations.builder()
                .reservationPeriod(reservationPeriod2)
                .member(member)
                .parkingLot(parkingLot)
                .build();

        parkingReservationRepository.save(reservations2);

        // when
        List<Reservations> reservedParkingLots = parkingReservationRepository.findOverlappingReservations(
                parkingLot.getId(), startTime, endTime);

        // then
        assertThat(reservedParkingLots).hasSize(2)
                .extracting("parkingLot.id", "reservationPeriod.startDateTime", "reservationPeriod.endDateTime")
                .containsExactlyInAnyOrder(
                        tuple(parkingLot.getId(), startTime, endTime),
                        tuple(parkingLot.getId(), startTime2, endTime2)
                );
    }


    private Locals createLocal(int mCode, int code, String name) {
        Locals local = new Locals(mCode, code, name);
        localsRepository.save(local);
        return local;
    }

    private Metropolitans createMetropolitan(int code, String name) {
        Metropolitans metropolitan = new Metropolitans(code, name);
        metropolitansRepository.save(metropolitan);
        return metropolitan;
    }

    private ContentTypes createContentType(String name) {
        ContentTypes contentType = new ContentTypes(name);
        contentTypesRepository.save(contentType);
        return contentType;
    }

    private Members createMember(String suffix) {
        Members member = new Members();
        member.setNickname("테스트" + suffix);
        member.setEmail("test@google.com" + suffix);
        member.setPassword("test1234" + suffix);
        member.setUsername("test" + suffix);
        return member;
    }
}