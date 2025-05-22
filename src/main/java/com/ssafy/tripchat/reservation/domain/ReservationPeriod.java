package com.ssafy.tripchat.reservation.domain;

import com.ssafy.tripchat.common.exception.MethodArgumentNotValidException;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ReservationPeriod {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public ReservationPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        validateReservationPeriod(startDateTime, endDateTime);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    private void validateReservationPeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if (startDateTime.isAfter(endDateTime)) {
            throw new MethodArgumentNotValidException("예약 시작 시간이 종료 시간보다 늦을 수 없습니다.");
        }

        // 정시 단위 검증
        if (startDateTime.getMinute() != 0 || endDateTime.getMinute() != 0) {
            throw new MethodArgumentNotValidException("예약 시작 시간과 종료 시간은 정시 단위여야 합니다.");
        }

        // 최대 예약시간 6시간 검증
        long hour = java.time.Duration.between(startDateTime, endDateTime).toHours();
        if (hour > 6) {
            throw new MethodArgumentNotValidException("예약 시간은 최대 6시간까지 가능합니다.");
        }
    }
}
