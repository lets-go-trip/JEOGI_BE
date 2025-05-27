package com.ssafy.tripchat.common.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAop {


    @Around("@annotation(com.ssafy.tripchat.common.aop.LogExecutionTime)")
    public Object executionTime(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        LogExecutionTime logExecutionTime = method.getAnnotation(LogExecutionTime.class);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = pjp.proceed();

        stopWatch.stop();

        log.info("Execution time of method {}: {} ms", method.getName(), stopWatch.getTotalTimeMillis());
        return proceed;
    }
}
