package com.ssafy.tripchat.reservation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.tripchat.global.config.SecurityConfig;
import com.ssafy.tripchat.global.security.domain.MemberPrincipal;
import com.ssafy.tripchat.member.domain.Members;
import com.ssafy.tripchat.reservation.domain.ReservationPeriod;
import com.ssafy.tripchat.reservation.dto.request.ParkingReservationRequest;
import com.ssafy.tripchat.reservation.dto.response.ParkingReservationResponse;
import com.ssafy.tripchat.reservation.service.ParkingLotReservationService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(controllers = ParkingLotReservationController.class)
@Import(SecurityConfig.class)
class ParkingLotReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ParkingLotReservationService parkingLotReservationService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @DisplayName("주차 예약을 성공적으로 수행한다.")
    @Test
    public void doReserve() throws Exception {
        // given
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1L);

        ParkingReservationResponse response = ParkingReservationResponse.builder()
                .parkingLotId(1)
                .reservationPeriod(new ReservationPeriod(startTime, endTime))
                .build();

        ParkingReservationRequest request = ParkingReservationRequest.builder()
                .startDateTime(startTime)
                .endDateTime(endTime)
                .build();

        when(parkingLotReservationService.reserveParkingLot(anyInt(), anyInt(), any(ParkingReservationRequest.class)))
                .thenReturn(response);

        Members member = createMember("1");
        // when

        mockMvc.perform(
                        post("/api/v1/parking-lots/{parkingLotId}/reservation", 1)
                                .with(SecurityMockMvcRequestPostProcessors.user(new MemberPrincipal(member)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.parkingLotId").value(1));

    }

    @DisplayName("예약 종료시간을 명시하지 않은 경우 예외가 발생한다.")
    @Test
    public void cannotRequestReservation_whenNotExistEndTime() throws Exception {
        // given
        LocalDateTime startTime = LocalDateTime.now().withMinute(0).withSecond(0).plusHours(1);
        LocalDateTime endTime = startTime.plusHours(1L);

        ParkingReservationResponse response = ParkingReservationResponse.builder()
                .parkingLotId(1)
                .reservationPeriod(new ReservationPeriod(startTime, endTime))
                .build();

        ParkingReservationRequest request = ParkingReservationRequest.builder()
                .endDateTime(endTime)
                .build();

        when(parkingLotReservationService.reserveParkingLot(anyInt(), anyInt(), any(ParkingReservationRequest.class)))
                .thenReturn(response);

        Members member = createMember("1");
        // when
        mockMvc.perform(
                        post("/api/v1/parking-lots/{parkingLotId}/reservation", 1)
                                .with(SecurityMockMvcRequestPostProcessors.user(new MemberPrincipal(member)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("예약할 날짜와 시작 시간은 필수입니다."));
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