package com.ssafy.tripchat.chat.infrastructure;

import com.ssafy.tripchat.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, ChatMessage> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessage message) {
        log.info("published topic = {}", topic.getTopic());
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
