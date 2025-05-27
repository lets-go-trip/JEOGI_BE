package com.ssafy.tripchat.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 4xx
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_REQUEST"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "INVALID_INPUT");

    private final HttpStatus status;
    private final String code;

}
