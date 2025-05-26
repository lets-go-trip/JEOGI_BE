package com.ssafy.tripchat.chat.controller;


import com.ssafy.tripchat.chat.domain.ChatMessage;
import com.ssafy.tripchat.chat.domain.Type;
import com.ssafy.tripchat.chat.dto.ChatMessageIncoming;
import com.ssafy.tripchat.chat.repository.ChatRoomRepository;
import com.ssafy.tripchat.chat.service.ChatService;
import com.ssafy.tripchat.common.aop.LogExecutionTime;
import com.ssafy.tripchat.common.exception.InvalidRequestException;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatRoomRepository chatRoomRepository;

    @LogExecutionTime
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, Principal principal) {
        log.info("Principal 정보 출력하기 : {}", principal.getName());

        if (principal == null) {
            throw new InvalidRequestException("인증되지 않은 사용자입니다.");
        }
  
        if (Type.ENTER.equals(message.getType())) {
            //TODO 존재하지 않는 채팅방인 경우 생성하고 들어간다.
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
            log.info("{}: User {} entered room {}", message.getCreatedAt(), message.getSender(), message.getRoomId());
        }
        chatService.sendMessage(message);
    }
}