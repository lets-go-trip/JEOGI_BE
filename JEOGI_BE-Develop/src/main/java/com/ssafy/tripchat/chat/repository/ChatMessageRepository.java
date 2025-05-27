package com.ssafy.tripchat.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.tripchat.chat.domain.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    List<ChatMessage> findAllByRoomId(Integer roomId);

    List<ChatMessage> findTop20ByRoomIdAndIdLessThanOrderByIdDesc(Integer roomId, Integer id);
}
