package com.ssafy.tripchat.chat.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.tripchat.chat.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    //TODO : RedisTemplate<String, ChatRoom>로 변경 (명시하지 않아도 타입으로 매핑하도록)
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messageTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = String.valueOf(redisTemplate.getStringSerializer().deserialize(message.getBody()));
            log.info("Received message: {}", publishMessage);

            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);
            log.info("Deserialized message: {}", roomMessage.getMessage());

            messageTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);
            log.info("Message sent to /sub/chat/room/{}", roomMessage.getRoomId());
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
        }
    }
}
//TODO: 메시지 DB 저장 및 불러오기, 채팅방 내역 레디스 저장
