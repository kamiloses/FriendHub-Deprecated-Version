package com.application.friendhub.websocket;

import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.websocket.chat.AvailableUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j

public class WebSocketEventListener {


    private final SimpMessageSendingOperations messagingTemplate;
    private AvailableUserService availableUserService;
    private UserRepository userRepository;

    public WebSocketEventListener(SimpMessageSendingOperations messagingTemplate, AvailableUserService availableUserService, UserRepository userRepository) {
        this.messagingTemplate = messagingTemplate;
        this.availableUserService = availableUserService;
        this.userRepository = userRepository;
    }

    String user;

   @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
       String loggedUser = headerAccessor.getUser().getName();
       UserEntity userEntity = userRepository.findUserEntityByEmail(loggedUser).orElseThrow(() -> new RuntimeException("User not found"));
         availableUserService.addUser(userEntity.getEmail(),userEntity.getId());


        log.error(userEntity.getUserDetailsEntity().getFirstName() +" "+  userEntity.getUserDetailsEntity().getLastName() + " has been connected");

   }





    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String loggedUser = headerAccessor.getUser().getName();
        UserEntity userEntity = userRepository.findUserEntityByEmail(loggedUser).orElseThrow(() -> new RuntimeException("User not found"));
        availableUserService.removeUser(userEntity.getEmail(),userEntity.getId());

        log.error(userEntity.getUserDetailsEntity().getFirstName() +" "+  userEntity.getUserDetailsEntity().getLastName() + " has been disconnected");


    }


}
