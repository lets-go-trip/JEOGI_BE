package com.ssafy.tripchat.global.config;

import com.ssafy.tripchat.common.dto.ErrorResponse;
import com.ssafy.tripchat.common.exception.BusinessException;
import com.ssafy.tripchat.common.exception.ErrorCode;
import com.ssafy.tripchat.common.exception.MethodArgumentNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        return ErrorResponse.toResponseEntity(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ErrorResponse.toResponseEntity(ErrorCode.INVALID_INPUT, ex.getMessage());
    }

}
