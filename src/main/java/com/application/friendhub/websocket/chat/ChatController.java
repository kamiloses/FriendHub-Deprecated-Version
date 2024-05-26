package com.application.friendhub.websocket.chat;

import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserRepository;
import lombok.ToString;
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
    private  AvailableUserService availableUserService;


    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(UserRepository userRepository, AvailableUserService availableUserService, SimpMessagingTemplate messagingTemplate) {
        this.userRepository = userRepository;
        this.availableUserService = availableUserService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.joinedUser")
    public void availableUser(Authentication authentication,SimpMessageHeaderAccessor headerAccessor) {
        UserEntity user1 = userRepository.findUserEntityByEmail(authentication.getName()).orElseThrow();


//        messagingTemplate.convertAndSend("/topic/public", new AvailableUserService(user1.getId(), "CONNECTED"));


        headerAccessor.getSessionAttributes().put("username", authentication.getName());
        UserEntity user = userRepository.findUserEntityByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));
        user.getId();
        log.error(authentication.getName());
       /* messagingTemplate.convertAndSend("/topic/public", user.getId());*/
        /* availableUserService.addUser(authentication.getName(), user.getId());*/


    }



        @MessageMapping("/chat.sendMessage/{userId}")
//        @SendToUser("/queue/messages/user/{userId}")
        public ChatMessage sendMessage(@DestinationVariable String userId, @Payload ChatMessage message,Authentication authentication,SimpMessageHeaderAccessor headerAccessor) {

            UserEntity user = userRepository.findUserEntityByEmail(authentication.getName()).orElseThrow();
             message.setSender(user.getUserDetailsEntity().getFirstName() + " " + user.getUserDetailsEntity().getLastName());
            messagingTemplate.convertAndSend("/queue/messages/user/" + userId, message);



            return message;
        }










}


