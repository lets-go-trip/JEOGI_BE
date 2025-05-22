package com.ssafy.tripchat.chat.service;

import com.ssafy.tripchat.chat.domain.ChatMessage;
import com.ssafy.tripchat.chat.dto.ChatMessageListResponse;
import com.ssafy.tripchat.chat.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageListResponse fetchAllByRoomId(String roomId) {
        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByRoomId(roomId);
        return new ChatMessageListResponse(chatMessageList);
    }

    public ChatMessageListResponse fetchWithCursorByRoomId(String roomId, int cursor) {
        if (cursor <= 0) return new ChatMessageListResponse(new ArrayList<ChatMessage>());
        List<ChatMessage> chatMessageList = chatMessageRepository.findTop20ByRoomIdAndIdLessThanOrderByIdDesc(roomId, cursor);
        return new ChatMessageListResponse(chatMessageList);
    }
}
