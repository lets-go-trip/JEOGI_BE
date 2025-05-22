package com.ssafy.tripchat.chat.config;

import com.ssafy.tripchat.chat.domain.ChatMessage;
import com.ssafy.tripchat.chat.domain.ChatRoom;
import com.ssafy.tripchat.chat.infrastructure.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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

    @Bean(name = "chatRoomRedisTemplate")
    public RedisTemplate<String, Object> chatRoomRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // 값 설정을 위한 직렬화
        Jackson2JsonRedisSerializer<ChatRoom> serializer = new Jackson2JsonRedisSerializer<>(ChatRoom.class);
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        return template;
    }

    @Bean(name = "chatMessageRedisTemplate")
    public RedisTemplate<String, ChatMessage> chatMessageRedisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, ChatMessage> template = new RedisTemplate<>();
        template.setConnectionFactory(cf);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessage.class));
        return template;
    }
}
