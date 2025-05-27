package com.ssafy.tripchat.common.aop;

import java.lang.reflect.Method;
import java.util.concurrent.locks.ReentrantLock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ssafy.tripchat.common.elparser.CustomSpELParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LockAop {

    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final LockManager lockManager = new LockManager();

    @Around("@annotation(com.ssafy.tripchat.common.aop.WithLock)")
    public Object getLockAndRelease(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        WithLock withLock = method.getAnnotation(WithLock.class);

        String value = CustomSpELParser.getDynamicValue(signature.getParameterNames(), pjp.getArgs(),
                withLock.key(), String.class);

        lockManager.lock(value);
        try {
            return pjp.proceed();
        } finally {
            lockManager.unlock(value);
        }
    }
}


