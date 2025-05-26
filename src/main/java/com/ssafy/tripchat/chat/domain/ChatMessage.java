package com.ssafy.tripchat.chat.domain;

import java.io.Serializable;
import com.ssafy.tripchat.chat.dto.ChatMessageIncoming;
import com.ssafy.tripchat.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private Type type;

    private Integer roomId;

    private int senderId;

    private String sender;

    private String message;

    //@CreationTimestamp
    //private LocalDateTime createdAt;

    //TODO : 작성시간 BaseEntity 상속 받아서 사용 -> 채팅은 수정할 수 없으니까 createdAt 하나만 있어도 되지않을까..?
}
