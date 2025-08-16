package com.project.crowdfunding.interceptor;

import com.project.crowdfunding.Services.AuthService.JwtService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.security.Principal;

public class JwtAuthChannelInterceptor implements ChannelInterceptor {

    private final JwtService jwtService;

    public JwtAuthChannelInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
//            String authHeader = accessor.getFirstNativeHeader("Authorization");
            String username = (String)accessor.getSessionAttributes().get("username");
          if(username != null){
              accessor.setUser(new Principal() {
                  @Override
                  public String getName() {
                      return username;
                  }
              });
          }
        }

        return message;
    }
}

