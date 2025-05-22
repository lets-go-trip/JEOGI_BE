package com.ssafy.tripchat.chat.service;

import com.ssafy.tripchat.chat.domain.ChatRoom;
import com.ssafy.tripchat.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRoomRepository chatRoomRepository;

    @Override
    public ChatRoom saveChatRoom(ChatRoom chatRoom) {
        log.info("Saving chat room: {} , room name is {} : ", chatRoom, chatRoom.getName());
        return chatRoomRepository.save(chatRoom);
    }
}
