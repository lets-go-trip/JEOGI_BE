package com.ssafy.tripchat.reservation.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingReservationRequest {
    
    @NotNull(message = "예약할 날짜와 시작 시간은 필수입니다.")
    @Future(message = "예약 시작 시간은 현재 이후여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateTime;

    @NotNull(message = "예약할 날짜와 종료 시간은 필수입니다.")
    @Future(message = "예약 종료 시간은 현재 이후여야 합니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDateTime;

}
