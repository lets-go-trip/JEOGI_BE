package com.ssafy.tripchat.chat.dto;

import java.io.Serializable;

import com.ssafy.tripchat.chat.domain.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageIncoming implements Serializable {

    private Type type;

    private String roomId;

    private int senderId;

    private String sender;

    private String message;

}

