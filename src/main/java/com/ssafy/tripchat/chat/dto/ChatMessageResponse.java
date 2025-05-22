package com.ssafy.tripchat.chat.dto;

import com.ssafy.tripchat.chat.domain.ChatMessage;
import com.ssafy.tripchat.chat.domain.Type;
import lombok.Getter;

@Getter
public class ChatMessageResponse {
    private int id;
    private Type type;
    private String roomId;
    private String sender;
    private String message;

    public ChatMessageResponse(ChatMessage chatMessage) {
        this.id = chatMessage.getId();
        this.type = chatMessage.getType();
        this.roomId = chatMessage.getRoomId();
        this.sender = chatMessage.getSender();
        this.message = chatMessage.getMessage();
    }
}
