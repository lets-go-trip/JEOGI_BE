package com.ssafy.tripchat.common.aop;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

@Component
public class LockManager {
    private final ConcurrentHashMap<String, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    public void lock(String key) {
        ReentrantLock lock = lockMap.computeIfAbsent(key, k -> new ReentrantLock());
        lock.lock();
    }

    public void unlock(String key) {
        ReentrantLock lock = lockMap.get(key);
        if (lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
