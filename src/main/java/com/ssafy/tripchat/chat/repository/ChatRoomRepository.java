package com.ssafy.tripchat.chat.repository;

import com.ssafy.tripchat.chat.domain.ChatRoom;
import java.util.List;
import org.springframework.data.redis.listener.ChannelTopic;

public interface ChatRoomRepository {

    ChannelTopic getTopic(String roomId);

    void enterChatRoom(String roomId);

    ChatRoom findRoomById(String roomId);

    List<ChatRoom> findAllRoom();

    ChatRoom save(ChatRoom chatRoom);
}
