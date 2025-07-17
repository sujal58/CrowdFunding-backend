package com.project.crowdfunding.Services.Redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.crowdfunding.dto.response.NotificationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class RedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String json = new String(message.getBody(), StandardCharsets.UTF_8);
            NotificationResponseDto dto = objectMapper.readValue(json, NotificationResponseDto.class);

            if (dto.isBroadcast()) {
                System.out.println("Received message in public: "+ json);
                messagingTemplate.convertAndSend("/broadcast/notifications", dto);
            } else {
                //Private notification to a specific user
                System.out.println("Received message in private: "+ json);
                System.out.println("connected user to send message: "+dto.getUsername());
                messagingTemplate.convertAndSendToUser(
                        dto.getUsername(),
                        "/queue/notifications",
                        dto
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


