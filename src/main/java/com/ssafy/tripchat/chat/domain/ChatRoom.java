package com.ssafy.tripchat.chat.domain;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roomId;
    private String name;

    @Builder
    private ChatRoom(Integer roomId, String name) {
        this.roomId = roomId;
        this.name = name;
    }

    public static ChatRoom create(Integer roomId, String name) {
        return ChatRoom.builder().roomId(roomId).name(name).build();
    }
}
