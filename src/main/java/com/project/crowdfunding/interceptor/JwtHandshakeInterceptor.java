package com.project.crowdfunding.interceptor;

import com.project.crowdfunding.Services.AuthService.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtService jwtService;

    public JwtHandshakeInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletRequest) {
//            String authHeader = servletRequest.getServletRequest().getHeader("Authorization");

            HttpServletRequest httpReq = servletRequest.getServletRequest();
            String token = httpReq.getParameter("token");
            System.out.println("handshake: "+ token);
            if (token != null && !token.isBlank()) {
                String username = jwtService.extractUsername(token);
                if (username != null) {
                    attributes.put("username", username); // pass to principal
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
        // No action
    }
}

