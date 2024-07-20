package com.application.friendhub.websocket.event;

import com.application.friendhub.Entity.PublicChatEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.websocket.config.AvailableUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;

@Component
@Slf4j

public class WebSocketEventListener {


    private final SimpMessageSendingOperations messagingTemplate;
    private final AvailableUserService availableUserService;
    private final UserRepository userRepository;

    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate, AvailableUserService availableUserService, UserRepository userRepository) {
        this.messagingTemplate = messagingTemplate;
        this.availableUserService = availableUserService;
        this.userRepository = userRepository;
    }


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String loggedUser = headerAccessor.getUser().getName();
        UserEntity userEntity = userRepository.findUserEntityByEmail(loggedUser).orElseThrow(() -> new RuntimeException("User not found"));
        messagingTemplate.convertAndSend("/topic/public", new AvailableUserService(userEntity.getId(), "CONNECTED"));

        log.error(userEntity.getUserDetailsEntity().getFirstName() + " " + userEntity.getUserDetailsEntity().getLastName() + " has been connected");
        availableUserService.userConnected(userEntity.getId());



    }


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String loggedUser = headerAccessor.getUser().getName();
        UserEntity userEntity = userRepository.findUserEntityByEmail(loggedUser).orElseThrow(() -> new RuntimeException("User not found"));
        messagingTemplate.convertAndSend("/topic/public", new AvailableUserService(userEntity.getId(), "DISCONNECTED"));


        log.error(userEntity.getUserDetailsEntity().getFirstName() + " " + userEntity.getUserDetailsEntity().getLastName() + " has been disconnected");
        availableUserService.userDisconnected(userEntity.getId());


        userEntity.setLastActivity(new Date());
        userRepository.save(userEntity);

    }

    @EventListener
    public void  handleWebSockedUserJoinedTheGroup(FriendJoinedTheGroupEvent event){

        PublicChatEntity publicChatEntity = event.getPublicChatEntity();
        UserEntity userEntity = event.getUserEntity();

   
        String message="User " + userEntity.getUserDetailsEntity().getFirstName() + " " + userEntity.getUserDetailsEntity().getLastName();

        //messagingTemplate.convertAndSend();
    }



}
