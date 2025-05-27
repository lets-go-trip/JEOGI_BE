package com.ssafy.tripchat.common.exception;

import lombok.Getter;

/**
 * @author : ho
 * @description : Spring Vailidation에서 발생하는 예외를 처리하기 위한 클래스
 * @date : 2025/05/13
 */

@Getter
public class MethodArgumentNotValidException extends RuntimeException {

    public MethodArgumentNotValidException(String message) {
        super(message);
    }
}
