package com.ssafy.tripchat.chat.controller;

import com.ssafy.tripchat.chat.dto.ChatMessageListResponse;
import com.ssafy.tripchat.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{roomId}/{cursor}")
    public ResponseEntity<?> fetchByRoomId(@PathVariable(name = "roomId") String roomId, @PathVariable(name = "cursor") String cursor) {
        if (cursor.equals("latest")) {
            cursor = "1000000";
        }
        System.out.println("cursor = " + cursor + " roomId = " + roomId);
        ChatMessageListResponse result = chatService.fetchWithCursorByRoomId(roomId, Integer.parseInt(cursor));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
