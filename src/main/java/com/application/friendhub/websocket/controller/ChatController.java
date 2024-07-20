package com.application.friendhub.websocket.controller;

import com.application.friendhub.Entity.MessagesEntity;
import com.application.friendhub.Entity.PrivateChatEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.RabbitMQ.RabbitMqQueueService;
import com.application.friendhub.RabbitMQ.RabbitMqSender;
import com.application.friendhub.RabbitMQ.RabbitService;
import com.application.friendhub.Repository.MessageRepository;
import com.application.friendhub.Repository.PrivateChatRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.websocket.dto.ChatMessage;
import com.application.friendhub.websocket.config.AvailableUserService;
import com.application.friendhub.websocket.config.WebsocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
@Slf4j

public class ChatController {
    private final UserRepository userRepository;
    private final AvailableUserService availableUserService;
    private final MessageRepository messageRepository;
    private final PrivateChatRepository privateChatRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final RabbitMqQueueService rabbitMqQueueService;
    private final RabbitMqSender rabbitMqSender;
    private final WebsocketService websocketService;
    private final RabbitService rabbitService;

    public ChatController(UserRepository userRepository, AvailableUserService availableUserService, MessageRepository messageRepository, PrivateChatRepository privateChatRepository, SimpMessagingTemplate messagingTemplate, RabbitMqQueueService rabbitMqQueueService, RabbitMqSender rabbitMqSender, WebsocketService websocketService, RabbitService rabbitService) {
        this.userRepository = userRepository;
        this.availableUserService = availableUserService;
        this.messageRepository = messageRepository;
        this.privateChatRepository = privateChatRepository;
        this.messagingTemplate = messagingTemplate;
        this.rabbitMqQueueService = rabbitMqQueueService;
        this.rabbitMqSender = rabbitMqSender;
        this.websocketService = websocketService;
        this.rabbitService = rabbitService;
    }





    @MessageMapping("/chat.sendMessage/{userId}")
//        @SendToUser("/queue/messages/user/{userId}")
    public ChatMessage sendMessage(@DestinationVariable String userId, @Payload ChatMessage message, Authentication authentication, SimpMessageHeaderAccessor headerAccessor) {
        UserEntity myAccount = userRepository.findUserEntityByEmail(authentication.getName()).get();
        MessagesEntity messagesEntity = websocketService.setMessage(userId, message, myAccount);

        if (message.getIsPublicChat().equals("true")) {
            messagingTemplate.convertAndSend("/topic/messages/chat/" + userId, message);
            messageRepository.save(messagesEntity);

        } else {
            UserEntity referencedUser = userRepository.findById(Long.parseLong(userId)).get();
            PrivateChatEntity privateChatId = privateChatRepository.findPrivateChatEntitiesByUser1_IdAndUser2_Id(myAccount.getId(), referencedUser.getId());
            message.setChatId(privateChatId.getId());

            messagingTemplate.convertAndSend("/queue/messages/user/" + privateChatId.getId(), message);
            MessagesEntity sentMessage = messageRepository.save(messagesEntity);

            rabbitMqQueueService.addNewQueue(myAccount.getId() + ":" + userId, false);
            rabbitMqSender.sendToRabbitWithQueueName(myAccount.getId() + ":" + userId, sentMessage.getId());

        }
        return message;
    }

    @MessageMapping("/chat.receiveRabbitMq/{userId}")
    public void receiveRabbitMq(@DestinationVariable String userId, Authentication authentication) {
        String receiverEmail = authentication.getName();

        rabbitService.SaveReceivedMessagesDataToDatabase(userId, receiverEmail);


    }


    @MessageMapping("/chat.requestActiveUsers")
    public void handleRequestActiveUsers() {

        HashMap<Long, String> onlineUsers = availableUserService.getOnlineUsers();

        List<Long> allActiveUsers = new ArrayList<>(onlineUsers.keySet());

        messagingTemplate.convertAndSend("/topic/public/ActiveUsersAtStart", allActiveUsers);

    }





    //todo zaimplementuj  wiadomosc ze uzytkownik dołączył do grupy

    @MessageMapping("/chat.joinedUser")
    public void availableUser(Authentication authentication, SimpMessageHeaderAccessor headerAccessor) {
        UserEntity user1 = userRepository.findUserEntityByEmail(authentication.getName()).orElseThrow();

        headerAccessor.getSessionAttributes().put("username", authentication.getName());
        UserEntity user = userRepository.findUserEntityByEmail(authentication.getName()).orElseThrow(() -> new RuntimeException("User not found"));


    }

}


