package com.ssafy.tripchat.reservation.domain;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationPeriod {

    // 시간 단위로 예약을 하기 때문에 LocalDateTime으로 설정
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
