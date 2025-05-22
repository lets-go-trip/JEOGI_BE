package com.ssafy.tripchat.chat.controller;


import com.ssafy.tripchat.chat.domain.ChatMessage;
import com.ssafy.tripchat.chat.domain.ChatRoom;
import com.ssafy.tripchat.chat.domain.Type;
import com.ssafy.tripchat.chat.infrastructure.RedisMessageCacheManager;
import com.ssafy.tripchat.chat.infrastructure.RedisPublisher;
import com.ssafy.tripchat.chat.repository.ChatRoomRepository;
import com.ssafy.tripchat.chat.service.ChatService;
import com.ssafy.tripchat.common.aop.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatRoomRepository chatRoomRepository;
    private final RedisPublisher redisPublisher;
    private final ChatService chatService;
    private final RedisMessageCacheManager redisMessageCacheManager;

    @LogExecutionTime
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        // TODO: getSender().getNickname() 부분 적절한지 검토하기
        if (Type.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
            log.info("User {} entered room {}", message.getSender(), message.getRoomId());
        }

        // TODO: Send 처리하는 부분 Service로 분리하기
        // TODO: ENTER 메시지 DB에 안 올리는 게 적절한지 검토하기
//        if (message.getType().equals(Type.TALK)) {
//            chatMessageRepository.save(message);
//        }
        redisMessageCacheManager.addMessage(message.getRoomId(), message);
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }

    @PostMapping("/api/v1/chatroom")
    public void createChatRoom(ChatRoom chatRoom) {
        chatService.saveChatRoom(chatRoom);
    }
}