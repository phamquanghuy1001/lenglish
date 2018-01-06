package com.lenglish.web.websocket;

import com.lenglish.web.websocket.dto.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;


@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        org.springframework.security.authentication.UsernamePasswordAuthenticationToken pricipleFromEvent = (UsernamePasswordAuthenticationToken) event.getUser();
        org.springframework.security.core.userdetails.User userFromEvent = (org.springframework.security.core.userdetails.User) pricipleFromEvent.getPrincipal();
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(ChatMessage.MessageType.JOIN);
        chatMessage.setSender(userFromEvent.getUsername());

        String destination = headerAccessor.getDestination();

        messagingTemplate.convertAndSend(destination, chatMessage);
        logger.info("SUBSCRIBE");
    }
    @EventListener
    public void handleWebSocketUnSubscribeListener(SessionUnsubscribeEvent event) {
        org.springframework.security.authentication.UsernamePasswordAuthenticationToken pricipleFromEvent = (UsernamePasswordAuthenticationToken) event.getUser();
        org.springframework.security.core.userdetails.User userFromEvent = (org.springframework.security.core.userdetails.User) pricipleFromEvent.getPrincipal();
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(ChatMessage.MessageType.LEAVE);
        chatMessage.setSender(userFromEvent.getUsername());

        String destination = headerAccessor.getDestination();

        messagingTemplate.convertAndSend(destination, chatMessage);
        logger.info("UNSUBSCRIBE");
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
//        org.springframework.security.authentication.UsernamePasswordAuthenticationToken pricipleFromEvent = (UsernamePasswordAuthenticationToken) event.getUser();
//        org.springframework.security.core.userdetails.User userFromEvent = (org.springframework.security.core.userdetails.User) pricipleFromEvent.getPrincipal();
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setType(ChatMessage.MessageType.JOIN);
//        chatMessage.setSender(userFromEvent.getUsername());
//
//        String destination = headerAccessor.getDestination();
//
//        messagingTemplate.convertAndSend(destination, chatMessage);
        logger.info("CONNECT");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        org.springframework.security.authentication.UsernamePasswordAuthenticationToken pricipleFromEvent = (UsernamePasswordAuthenticationToken) event.getUser();
        org.springframework.security.core.userdetails.User userFromEvent = (org.springframework.security.core.userdetails.User) pricipleFromEvent.getPrincipal();
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(ChatMessage.MessageType.LEAVE);
        chatMessage.setSender(userFromEvent.getUsername());

        String destination = headerAccessor.getDestination();
        try {        	
        	messagingTemplate.convertAndSend(destination, chatMessage);
        } catch (Exception e) {
        	logger.error("Error Event Listener");
        }
        logger.info("DISCONNECT");
    }

}
