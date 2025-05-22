package com.ssafy.tripchat.chat.repository;

import com.ssafy.tripchat.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
}
