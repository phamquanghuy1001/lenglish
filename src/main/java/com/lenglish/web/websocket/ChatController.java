package com.lenglish.web.websocket;

import com.lenglish.web.websocket.dto.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload String message,
                                   SimpMessageHeaderAccessor headerAccessor) {
        org.springframework.security.authentication.UsernamePasswordAuthenticationToken pricipleFromHeader = (UsernamePasswordAuthenticationToken) headerAccessor.getUser();
        org.springframework.security.core.userdetails.User userFromHeader = (org.springframework.security.core.userdetails.User) pricipleFromHeader.getPrincipal();

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(message);
        chatMessage.setType(ChatMessage.MessageType.CHAT);
        chatMessage.setSender(userFromHeader.getUsername());
        return chatMessage;
    }
}
