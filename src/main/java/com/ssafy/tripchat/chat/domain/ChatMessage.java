package com.ssafy.tripchat.chat.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


//TODO : Entity랑 DTO 분리하기
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage implements Serializable {
    //TODO : Entity로 만들면 ID 필요함

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private Type type;

    //TODO: roomId integer 아니었나?
    private String roomId;

    //TODO: Members 객체로 바꾸기
    private String sender;

    private String message;

    //@CreationTimestamp
    //private LocalDateTime createdAt;

    //TODO : 작성시간 BaseEntity 상속 받아서 사용 -> 채팅은 수정할 수 없으니까 createdAt 하나만 있어도 되지않을까..?
}
