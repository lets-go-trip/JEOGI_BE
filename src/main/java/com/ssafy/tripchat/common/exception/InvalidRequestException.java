package com.ssafy.tripchat.common.exception;

public class InvalidRequestException extends BusinessException {
    public InvalidRequestException(String message) {
        super(ErrorCode.INVALID_REQUEST, message);
    }
}
