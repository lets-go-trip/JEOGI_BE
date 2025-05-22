package com.ssafy.tripchat.chat.config;

import com.ssafy.tripchat.chat.infrastructure.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChatRedisConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("chatroom");
    }

    // redis 메시지를 수신하고 리스너에 전달하는 컨테이너 설정
    @Bean
    public RedisMessageListenerContainer redisMessage(
            MessageListenerAdapter listenerAdapter, ChannelTopic channelTopic
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory); // redis connection 정보기반으로 설정
        container.addMessageListener(listenerAdapter, channelTopic); // 감지할 토픽을 설정
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapterChatMessage(RedisSubscriber subscriber) {
        return new MessageListenerAdapter(subscriber, "onMessage");
    }
}
