package com.ssafy.tripchat.global.service;

import com.google.gson.Gson;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCommon {

    private final RedisTemplate<String, String> template;
    private final Gson gson;

    //TODO 수정하기
    @Value("50m")
    private Duration TEMP_EXPIRE_TIME;

    public <T> T getData(String key, Class<T> clazz) {
        String jsonValue = template.opsForValue().get(key);

        if (jsonValue == null) {
            //TODO : 예외처리하기
            log.error("Redis에서 데이터를 찾을 수 없습니다. Key: {}", key);
            return null;
        }

        return gson.fromJson(jsonValue, clazz);
    }

    public <T> void setData(String key, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForValue().set(key, jsonValue);
        template.expire(key, TEMP_EXPIRE_TIME); // 1일
    }

    public <T> void putInHash(String key, String field, T Value) {
        String jsonValue = gson.toJson(Value);
        template.opsForHash().put(key, field, jsonValue);
    }

    public <T> T getFromHash(String key, String field, Class<T> clazz) {
        Object result = template.opsForHash().get(key, field);

        if (result != null) {
            return gson.fromJson(result.toString(), clazz);
        }

        //TODO : 없는거 조회하는 경우 예외처리
        return null;
    }

    public <T> void publish(String topic, T message) {
        String jsonValue = gson.toJson(message);
        log.info("published message = {}", message);
        template.convertAndSend(topic, jsonValue);
    }

    public Long getListSize(String key) {
        return template.opsForList().size(key);
    }

    public <T> void addToListLeft(String key, T value) {
        String jsonValue = gson.toJson(value);
        template.opsForList().leftPush(key, jsonValue);
    }

    public void removeToListRight(String key) {
        template.opsForList().rightPop(key);
    }
}
