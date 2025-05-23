package com.ssafy.tripchat.chat.service;

import com.ssafy.tripchat.chat.domain.ChatMessage;
import com.ssafy.tripchat.chat.domain.ChatRoom;
import com.ssafy.tripchat.chat.domain.Type;
import com.ssafy.tripchat.chat.dto.ChatMessageListResponse;
import com.ssafy.tripchat.chat.infrastructure.RedisMessageCacheManager;
import com.ssafy.tripchat.chat.infrastructure.RedisPublisher;
import com.ssafy.tripchat.chat.repository.ChatMessageRepository;
import com.ssafy.tripchat.chat.repository.ChatRoomRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RedisPublisher redisPublisher;
    private final RedisMessageCacheManager redisMessageCacheManager;


    @Override
    public ChatRoom saveChatRoom(ChatRoom chatRoom) {
        log.info("Saving chat room: {} , room name is {} : ", chatRoom, chatRoom.getName());
        return chatRoomRepository.save(chatRoom);
    }

    @Override
    public ChatMessageListResponse fetchAllByRoomId(String roomId) {
        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByRoomId(roomId);
        return new ChatMessageListResponse(chatMessageList);
    }

    @Override
    public ChatMessageListResponse fetchWithCursorByRoomId(String roomId, int cursor) {
        if (cursor <= 0) {
            return new ChatMessageListResponse(new ArrayList<ChatMessage>());
        }
        List<ChatMessage> chatMessageList = chatMessageRepository.findTop20ByRoomIdAndIdLessThanOrderByIdDesc(roomId,
                cursor);
        return new ChatMessageListResponse(chatMessageList);
    }

    public void sendMessage(ChatMessage message) {
        if (message.getType().equals(Type.TALK)) {
            chatMessageRepository.save(message);
            redisMessageCacheManager.addMessage(message.getRoomId(), message);
        }
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }

    public ChatMessageListResponse fetchMessagesFromCache(String roomId) {
        List<ChatMessage> chatMessageList = redisMessageCacheManager.getMessages(roomId, ChatMessage.class);
        return new ChatMessageListResponse(chatMessageList);
    }
}
