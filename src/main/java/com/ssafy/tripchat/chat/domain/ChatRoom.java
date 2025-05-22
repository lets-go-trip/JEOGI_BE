package com.ssafy.tripchat.chat.domain;

import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    private String roomId;
    private String name;

    @Builder
    private ChatRoom(String roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public static ChatRoom create(String name) {
        return ChatRoom.builder().roomId(UUID.randomUUID().toString()).name(name).build();
    }
}
