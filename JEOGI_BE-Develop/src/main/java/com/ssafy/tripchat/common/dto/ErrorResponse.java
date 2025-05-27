package com.ssafy.tripchat.common.dto;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;

import com.ssafy.tripchat.common.exception.ErrorCode;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

    private final String timestamp;
    private final int status;
    private final String code;
    private final String message;
    private final String path;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode, String message) {

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toString())
                .status(errorCode.getStatus().value())
                .code(errorCode.getCode())
                .message(message)
                .build();

        return ResponseEntity.status(errorCode.getStatus()).body(body);
    }
}
