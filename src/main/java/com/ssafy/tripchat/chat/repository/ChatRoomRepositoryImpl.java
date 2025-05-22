package com.ssafy.tripchat.chat.repository;

import com.ssafy.tripchat.chat.domain.ChatRoom;
import com.ssafy.tripchat.chat.infrastructure.RedisSubscriber;
import com.ssafy.tripchat.global.service.RedisCommon;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepository {
    public static final String CHAT_ROOM = "CHAT_ROOM";


    private final RedisCommon redis;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        topics = new ConcurrentHashMap<>();
        opsHashChatRoom = redisTemplate.opsForHash();

        //CHAT_ROOMS에 있는 모든 방을 가져와서 채널 토픽을 생성
        Set<String> roomIds = null;
//        Set<String> roomIds = redisTemplate.opsForSet().members(CHAT_ROOM);

        if (roomIds != null) {
            for (Object roomIdObj : roomIds) {
                String roomId = String.valueOf(roomIdObj);
                ChannelTopic topic = new ChannelTopic(roomId);
                redisMessageListener.addMessageListener(redisSubscriber, topic);
                topics.put(roomId, topic);
            }
        }

        log.info("ChatRoomRepository initialized");
    }

    public ChatRoom save(ChatRoom chatRoom) {
        redis.putInHash(CHAT_ROOM, chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    public List<ChatRoom> findAllRoom() {
        log.info("Fetching all chat rooms.");
        return opsHashChatRoom.values(CHAT_ROOM);
    }

    public ChatRoom findRoomById(String id) {
        return opsHashChatRoom.get(CHAT_ROOM, id);
    }

    // 서버가 채팅방 공유를 위해 redis hash에 저장
    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        opsHashChatRoom.put(CHAT_ROOM, chatRoom.getRoomId(), chatRoom);

        return chatRoom;
    }

    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);

        if (topic == null) {
            topic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, topic);
            topics.put(roomId, topic);
        } else {
            log.info("{} 라는 이름으로 이미 존재하는 방이 있습니다.", roomId);
        }
    }

    public ChannelTopic getTopic(String roomId) {
        ChannelTopic topic = topics.get(roomId);

        if (topic == null) {
            log.error("Topic not found for roomId={}", roomId);
        }
        return topic;
    }
}
