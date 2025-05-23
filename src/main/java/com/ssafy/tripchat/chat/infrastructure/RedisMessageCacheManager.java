package com.ssafy.tripchat.chat.infrastructure;

import com.ssafy.tripchat.global.service.RedisCommon;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RedisMessageCacheManager {

    private final RedisCommon redis;

    private static final int MAX_MESSAGE_COUNT = 100;

    public <T> void addMessage(String roomId, T message) {
        String key = getMessageKey(roomId);
        Long size = redis.getListSize(key);

        redis.addToListLeft(key, message);
        redis.expire(key, Duration.ofDays(3));

        if (size != null && size > MAX_MESSAGE_COUNT) {
            redis.removeToListRight(key);
        }

        log.info("roomId : {} :: 메시지 캐싱 성공 : {}", roomId, message);
    }

    public <T> List<T> getMessages(String roomId, Class<T> clazz) {
        String key = getMessageKey(roomId);
        return redis.getAllList(key, clazz);
    }

    private String getMessageKey(String roomId) {
        return "chat:roomId:" + roomId + ":messages";
    }
}
