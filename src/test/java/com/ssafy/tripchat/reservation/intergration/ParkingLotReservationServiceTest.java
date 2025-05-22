package com.ssafy.tripchat.reservation.intergration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ssafy.tripchat.common.exception.InvalidRequestException;
import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.member.domain.MembersRepository;
import com.ssafy.tripchat.reservation.domain.ParkingLots;
import com.ssafy.tripchat.reservation.dto.request.ParkingReservationRequest;
import com.ssafy.tripchat.reservation.dto.response.ParkingReservationResponse;
import com.ssafy.tripchat.reservation.repository.ParkingLotsRepository;
import com.ssafy.tripchat.reservation.repository.ParkingReservationRepository;
import com.ssafy.tripchat.reservation.service.ParkingLotReservationService;
import com.ssafy.tripchat.travel.domain.Attractions;
import com.ssafy.tripchat.travel.domain.ContentTypes;
import com.ssafy.tripchat.travel.domain.Locals;
import com.ssafy.tripchat.travel.domain.Metropolitans;
import com.ssafy.tripchat.travel.repository.AttractionsRepository;
import com.ssafy.tripchat.travel.repository.ContentTypesRepository;
import com.ssafy.tripchat.travel.repository.LocalsRepository;
import com.ssafy.tripchat.travel.repository.MetropolitansRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ParkingLotReservationServiceTest {

    @Autowired
    private ParkingLotsRepository parkingLotsRepository;

    @Autowired
    private ParkingReservationRepository parkingReservationRepository;

    @Autowired
    private MembersRepository membersRepository;

    @Autowired
    private AttractionsRepository attractionsRepository;

    @Autowired
    private ContentTypesRepository contentTypesRepository;

    @Autowired
    private LocalsRepository localsRepository;

    @Autowired
    private MetropolitansRepository metropolitansRepository;

    @Autowired
    ParkingLotReservationService parkingService;

    @AfterEach
    public void tearDown() {
        // AutoIncrement는 초기화가 안되기 때문에 PK는 항상 getter를 사용하자
        parkingReservationRepository.deleteAllInBatch();
        parkingLotsRepository.deleteAllInBatch();
        attractionsRepository.deleteAllInBatch();
        contentTypesRepository.deleteAllInBatch();
        localsRepository.deleteAllInBatch();
        metropolitansRepository.deleteAllInBatch();
        membersRepository.deleteAllInBatch();
    }

    @DisplayName("10명의 사용자가 동시에 5 자리남은 주차장에 예약을 시도한다.")
    @Test
    public void tryReserveParkingSpace() throws Exception {
        // given
        // 주차장 1개 생성
        ContentTypes contentsType = createContentType("관광지");
        contentTypesRepository.save(contentsType);

        Metropolitans metropolitan = createMetropolitan(1, "서울특별시");
        metropolitansRepository.save(metropolitan);

        Locals local = createLocal(metropolitan.getCode(), 1, "노원구");
        localsRepository.save(local);

        Attractions attraction = new Attractions(metropolitan, contentsType, local, "제목", null, null, 123, 123,
                null, null, null);
        attractionsRepository.save(attraction);

        ParkingLots parkingLot = createParkingLot(attraction, 5);
        parkingLotsRepository.save(parkingLot);

        // 주차장 예약 요청 생성
        Members member = createMember("1");
        membersRepository.save(member);

        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(2L);

        ParkingReservationRequest parkingReservationRequest = createParkingReservationRequest(
                startTime, endTime);

        // when
        ParkingReservationResponse parkingReservationResponse = parkingService.reserveParkingLot(parkingLot.getId(),
                member.getId(),
                parkingReservationRequest);

        // then
        assertThat(parkingReservationResponse).extracting(
                        ParkingReservationResponse::getParkingLotId,
                        ParkingReservationResponse::getUsername,
                        r -> r.getReservationPeriod().getStartDateTime(),
                        r -> r.getReservationPeriod().getEndDateTime())
                .contains(1, "test1", startTime, endTime);
    }

    @DisplayName("5칸 남은 주차장에 10명이 동시에 예약을 시도하면 성공한 예약은 최대 5건이다.")
    @Test
    void tryReserveParkingSpace_concurrently() throws InterruptedException {
        // ── given ────────────────────────────────────────────────────────────────────
        int THREAD_COUNT = 10;
        int PARKING_CAPACITY = 5;   // parkingLot.totalCount

        // 1) 주차장-메타 데이터 준비
        ContentTypes contentsType = createContentType("관광지");
        contentTypesRepository.save(contentsType);

        Metropolitans metropolitan = createMetropolitan(1, "서울특별시");
        metropolitansRepository.save(metropolitan);

        Locals local = createLocal(metropolitan.getCode(), 1, "노원구");
        localsRepository.save(local);

        Attractions attraction = new Attractions(
                metropolitan, contentsType, local,
                "제목", null, null, 123, 123, null, null, null);
        attractionsRepository.save(attraction);

        ParkingLots parkingLot = createParkingLot(attraction, PARKING_CAPACITY);
        parkingLotsRepository.save(parkingLot);

        // 2) 10명의 회원 생성
        List<Members> members = IntStream.rangeClosed(1, THREAD_COUNT)
                .mapToObj(i -> createMember(String.valueOf(i)))
                .collect(Collectors.toList());
        membersRepository.saveAll(members);

        // 3) 공통 예약 파라미터
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(2);
        ParkingReservationRequest request = createParkingReservationRequest(
                startTime, endTime);

        // ── when  ───────────────────────────────────────────────────────────
        CountDownLatch startLatch = new CountDownLatch(1);      // 동시에 출발
        CountDownLatch doneLatch = new CountDownLatch(THREAD_COUNT); // 종료 대기
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (Members m : members) {
            executor.execute(new ReservationWorker(
                    2, m.getId(), request, startLatch, doneLatch));
        }

        startLatch.countDown();
        doneLatch.await();
        executor.shutdown();

        // ── then ─────────────────────────────────────────────────────────────────────
        int reservations =
                parkingReservationRepository.countParkingReservationInTimeRange(parkingLot.getId(), startTime, endTime);

        assertThat(reservations).isEqualTo(PARKING_CAPACITY);   // 실제 저장된 건수 = 최대 수용인원(5)
    }

    @DisplayName("동일 주차장, 동일 시간에 예약을 시도하면 예외가 발생한다.")
    @Test
    public void whenReservingSameParkingLotAtSameTime_thenThrowsException() throws Exception {
        // given
        // 주차장 1개 생성
        ContentTypes contentsType = createContentType("관광지");
        contentTypesRepository.save(contentsType);

        Metropolitans metropolitan = createMetropolitan(1, "서울특별시");
        metropolitansRepository.save(metropolitan);

        Locals local = createLocal(metropolitan.getCode(), 1, "노원구");
        localsRepository.save(local);

        Attractions attraction = new Attractions(metropolitan, contentsType, local, "제목", null, null, 123, 123,
                null, null, null);
        attractionsRepository.save(attraction);

        ParkingLots parkingLot = createParkingLot(attraction, 5);
        parkingLotsRepository.save(parkingLot);

        // 예약자 생성
        Members member = createMember("1");
        membersRepository.save(member);

        // 예약 요청 생성
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).plusHours(1);
        LocalDateTime endTime = LocalDateTime.now().withMinute(0).withSecond(0).plusHours(2);

        ParkingReservationRequest request = createParkingReservationRequest(
                startTime, endTime);

        parkingService.reserveParkingLot(parkingLot.getId(), member.getId(), request);

        // when // then
        assertThatThrownBy(() -> parkingService.reserveParkingLot(parkingLot.getId(), member.getId(), request))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("이미 동일 시간대 예약이 존재합니다.");
    }

    private Locals createLocal(int mCode, int code, String name) {
        return new Locals(mCode, code, name);
    }

    private Metropolitans createMetropolitan(int code, String name) {
        return new Metropolitans(code, name);
    }

    private ContentTypes createContentType(String name) {
        return new ContentTypes(name);
    }

    private ParkingLots createParkingLot(Attractions attraction, int totalSpaces) {
        return ParkingLots.builder()
                .totalCount(totalSpaces)
                .attraction(attraction)
                .build();
    }

    private ParkingReservationRequest createParkingReservationRequest(LocalDateTime startDateTime,
                                                                      LocalDateTime endDateTime) {
        return ParkingReservationRequest.builder()
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }

    private Members createMember(String suffix) {
        Members member = new Members();
        member.setNickname("테스트" + suffix);
        member.setEmail("test@google.com" + suffix);
        member.setPassword("test1234" + suffix);
        member.setUsername("test" + suffix);
        return member;
    }

    private class ReservationWorker implements Runnable {
        private final Integer parkingLotId;
        private final Integer memberId;
        private final ParkingReservationRequest req;
        private final CountDownLatch start;
        private final CountDownLatch done;

        ReservationWorker(
                Integer parkingLotId,
                Integer memberId,
                ParkingReservationRequest req,
                CountDownLatch start,
                CountDownLatch done) {
            this.parkingLotId = parkingLotId;
            this.memberId = memberId;
            this.req = req;
            this.start = start;
            this.done = done;
        }

        @Override
        public void run() {
            try {
                start.await();
                try {
                    parkingService.reserveParkingLot(parkingLotId, memberId, req);
                } catch (InvalidRequestException ignore) {
                    // 동시성을 보장하는 테스트를 위한 예외 처리
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                done.countDown();
            }
        }
    }
}