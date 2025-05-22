package com.ssafy.tripchat.chat.repository;

import com.ssafy.tripchat.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findAllByRoomId(String roomId);

    List<ChatMessage> findTop20ByRoomIdAndIdLessThanOrderByIdDesc(String roomId, Integer id);
}
