package com.ssafy.tripchat.chat.controller;


import com.ssafy.tripchat.chat.domain.ChatMessage;
import com.ssafy.tripchat.chat.domain.ChatRoom;
import com.ssafy.tripchat.chat.domain.Type;
import com.ssafy.tripchat.chat.infrastructure.RedisPublisher;
import com.ssafy.tripchat.chat.repository.ChatRoomRepository;
import com.ssafy.tripchat.chat.service.ChatService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomRepository chatRoomRepository;
    private final RedisPublisher redisPublisher;
    private final ChatService chatService;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @Header("simpSessionAttributes") Map<String, Object> sessionAttributes) {
        String memberId = (String) sessionAttributes.get("memberId");

        // TODO : socket sessionId로 memberId를 가져오는 로직 추가

        log.info("userID : {}", memberId);

        if (Type.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
            log.info("User {} entered room {}", message.getSender(), message.getRoomId());
        }

        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }

    @PostMapping("/api/v1/chatroom")
    public void createChatRoom(ChatRoom chatRoom) {
        chatService.saveChatRoom(chatRoom);

    }
}
