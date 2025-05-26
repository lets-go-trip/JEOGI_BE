package com.ssafy.tripchat.chat.service;

import com.ssafy.tripchat.chat.domain.ChatMessage;
import com.ssafy.tripchat.chat.domain.ChatRoom;
import com.ssafy.tripchat.chat.dto.ChatMessageIncoming;
import com.ssafy.tripchat.chat.dto.ChatMessageListResponse;

public interface ChatService {

    ChatRoom saveChatRoom(ChatRoom chatRoom);

    ChatMessageListResponse fetchAllByRoomId(Integer roomId);

    ChatMessageListResponse fetchWithCursorByRoomId(Integer roomId, int cursor);

    void sendMessage(ChatMessage message);

    ChatMessageListResponse fetchMessagesFromCache(String roomId);

    ChatMessage processMessage(ChatMessageIncoming messageIncoming);
}
