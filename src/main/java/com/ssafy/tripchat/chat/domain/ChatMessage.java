package com.ssafy.tripchat.chat.domain;

import com.ssafy.tripchat.chat.dto.ChatMessageIncoming;
import com.ssafy.tripchat.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


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

    //TODO: roomId integer 아니었나?
    private String roomId;

    private int senderId;

    private String sender;

    private String message;

}
