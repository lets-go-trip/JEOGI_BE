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

}
