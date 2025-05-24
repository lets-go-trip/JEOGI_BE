package com.ssafy.tripchat.chat.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import com.ssafy.tripchat.travel.domain.Attractions;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;

    @Builder
    private ChatRoom(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static ChatRoom create(String name) {
        return ChatRoom.builder().id(UUID.randomUUID().toString()).name(name).build();
    }

    public static ChatRoom createByAttr(Attractions attraction) {
        return ChatRoom.builder().id(String.valueOf(attraction.getId())).name(attraction.getTitle() + " 채팅방").build();
    }
}
