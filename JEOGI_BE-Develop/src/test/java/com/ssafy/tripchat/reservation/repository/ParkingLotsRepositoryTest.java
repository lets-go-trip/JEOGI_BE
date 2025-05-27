package com.ssafy.tripchat.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.ssafy.tripchat.common.exception.InvalidRequestException;
import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.reservation.domain.ParkingLots;
import com.ssafy.tripchat.travel.domain.Attractions;
import com.ssafy.tripchat.travel.domain.ContentTypes;
import com.ssafy.tripchat.travel.domain.Locals;
import com.ssafy.tripchat.travel.domain.Metropolitans;
import com.ssafy.tripchat.travel.repository.AttractionsRepository;
import com.ssafy.tripchat.travel.repository.ContentTypesRepository;
import com.ssafy.tripchat.travel.repository.LocalsRepository;
import com.ssafy.tripchat.travel.repository.MetropolitansRepository;

@ActiveProfiles("test")
@DataJpaTest
class ParkingLotsRepositoryTest {

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


    @DisplayName("관광지의 주차 가능 공간을 조회합니다.")
    @Test
    public void findAvailableParkingLot() throws Exception {

        // given
        ContentTypes contentsType = createContentType("관광지");
        contentTypesRepository.save(contentsType);

        Metropolitans metropolitan = createMetropolitan(1, "서울특별시");
        metropolitansRepository.save(metropolitan);

        Locals local = createLocal(metropolitan.getCode(), 1, "노원구");
        localsRepository.save(local);

        Attractions attraction = new Attractions(metropolitan, contentsType, local, "제목", null, null, 0, 0,
                null, null, null);
        attractionsRepository.save(attraction);

        ParkingLots parkingLot = ParkingLots.builder()
                .attraction(attraction)
                .totalCount(10)
                .build();
        parkingLotsRepository.save(parkingLot);

        // when
        ParkingLots foundParkingLot = parkingLotsRepository.findById(parkingLot.getId())
                .orElseThrow(() -> new RuntimeException("주차 공간을 찾을 수 없습니다."));

        // then
        assertThat(foundParkingLot).extracting("totalCount").isEqualTo(parkingLot.getTotalCount());

    }

    @DisplayName("존재하지 않는 주차장 조회시 예외를 발생합니다.")
    @Test
    public void findInValidParkingLot() throws Exception {

        // given
        ContentTypes contentsType = createContentType("관광지");
        contentTypesRepository.save(contentsType);

        Metropolitans metropolitan = createMetropolitan(1, "서울특별시");
        metropolitansRepository.save(metropolitan);

        Locals local = createLocal(metropolitan.getCode(), 1, "노원구");
        localsRepository.save(local);

        Attractions attraction = new Attractions(metropolitan, contentsType, local, "제목", null, null, 0, 0,
                null, null, null);
        attractionsRepository.save(attraction);

        ParkingLots parkingLot = ParkingLots.builder()
                .attraction(attraction)
                .totalCount(10)
                .build();

        parkingLotsRepository.save(parkingLot);

        int invalidId = -1;

        // when // then
        assertThatThrownBy(() -> parkingLotsRepository.findById(invalidId)
                .orElseThrow(() -> new InvalidRequestException("주차 공간을 찾을 수 없습니다.")))
                .isInstanceOf(InvalidRequestException.class)
                .hasMessage("주차 공간을 찾을 수 없습니다.");
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