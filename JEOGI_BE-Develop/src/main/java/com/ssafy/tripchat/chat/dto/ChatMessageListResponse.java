package com.ssafy.tripchat.chat.dto;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.tripchat.chat.domain.ChatMessage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageListResponse {
    private static final int MESSAGE_COUNT = 20;

    String message = "검색 성공";
    int length;
    int cursorNext;
    List<ChatMessageResponse> chatMessageList;

    public ChatMessageListResponse(List<ChatMessage> chatMessageList) {
        this.chatMessageList = new ArrayList<>();
        for (ChatMessage chatMessage : chatMessageList) {
            this.chatMessageList.add(new ChatMessageResponse(chatMessage));
        }
        this.length = this.chatMessageList.size();
        if (chatMessageList.size() < MESSAGE_COUNT) {
            this.cursorNext = 1;
            this.message = "검색 성공, 모든 메시지를 불러왔습니다";
        } else {
			this.cursorNext = chatMessageList.get(chatMessageList.size() - 1).getId();
		}
    }
}
