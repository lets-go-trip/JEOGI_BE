package com.ssafy.tripchat.chat.domain;

import jakarta.persistence.Embedded;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//TODO : Entity랑 DTO 분리하기
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage implements Serializable {
    //TODO : Entity로 만들면 ID 필요함
    @Embedded
    private Type type;
    private String roomId;
    private String sender;
    private String message;

    //TODO : 작성시간 BaseEntity 상속 받아서 사용
}
