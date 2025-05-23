package com.ssafy.tripchat.chat.controller;


import com.ssafy.tripchat.chat.domain.ChatMessage;
import com.ssafy.tripchat.chat.domain.Type;
import com.ssafy.tripchat.chat.repository.ChatRoomRepository;
import com.ssafy.tripchat.chat.service.ChatService;
import com.ssafy.tripchat.common.aop.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatRoomRepository chatRoomRepository;

    @PreAuthorize("USER")
    @LogExecutionTime
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        // TODO: getSender().getNickname() 부분 적절한지 검토하기
        if (Type.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
            log.info("User {} entered room {}", message.getSender(), message.getRoomId());
        }

        chatService.sendMessage(message);
    }
}