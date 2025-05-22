package com.ssafy.tripchat.chat.infrastructure;

import com.ssafy.tripchat.chat.domain.ChatMessage;
import com.ssafy.tripchat.global.service.RedisCommon;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisCommon redis;

    public void publish(ChannelTopic topic, ChatMessage message) {
        log.info("published topic = {}", topic.getTopic());
        redis.publish(topic.getTopic(), message);
    }
}
