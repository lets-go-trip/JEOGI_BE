package com.ssafy.tripchat.common.exception;

import lombok.Getter;

/**
 * @author : ho
 * @description : 비즈니스 로직에서 발생하는 예외
 * @date : 2025/05/13
 */

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
