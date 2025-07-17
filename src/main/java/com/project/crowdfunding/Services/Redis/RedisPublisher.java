package com.project.crowdfunding.Services.Redis;

import com.project.crowdfunding.dto.response.NotificationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ChannelTopic topic;

    public void publish(NotificationResponseDto message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}

