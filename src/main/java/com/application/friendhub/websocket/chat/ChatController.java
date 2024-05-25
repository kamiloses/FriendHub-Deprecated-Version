package com.application.friendhub.websocket.chat;

import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class ChatController {

    private UserRepository userRepository;

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }





        @MessageMapping("/chat.sendMessage/{userId}")
        @SendToUser("/queue/messages/user/{userId}")
        public String sendMessage(@DestinationVariable String userId, @Payload String message) {
            messagingTemplate.convertAndSend("/queue/messages/user/" + userId, message);
            return message;
        }










}


