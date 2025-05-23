package com.ssafy.tripchat.chat.controller;

import com.ssafy.tripchat.chat.dto.ChatMessageListResponse;
import com.ssafy.tripchat.chat.service.ChatService;
import com.ssafy.tripchat.common.aop.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRestController {
    private final ChatService chatService;

    @PreAuthorize("USER")
    @LogExecutionTime
    @GetMapping("/{roomId}/{cursor}")
    public ResponseEntity<?> fetchByRoomId(@PathVariable(name = "roomId") String roomId,
                                           @PathVariable(name = "cursor") String cursor) {
        if (cursor.equals("latest")) {
            ChatMessageListResponse result = chatService.fetchMessagesFromCache(roomId);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }

        log.info("cursor = {} roomId = {}", cursor, roomId);
        //TODO : redis에서 캐싱된 데이터를 가져오고 부족한건 DB에서 가져오기
        ChatMessageListResponse result = chatService.fetchWithCursorByRoomId(roomId, Integer.parseInt(cursor));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}



