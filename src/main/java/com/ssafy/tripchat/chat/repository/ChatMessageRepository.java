package com.ssafy.tripchat.chat.repository;

import com.ssafy.tripchat.chat.domain.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findAllByRoomId(Integer roomId);

    List<ChatMessage> findTop20ByRoomIdAndIdLessThanOrderByIdDesc(Integer roomId, Integer id);
}
