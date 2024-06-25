package com.application.friendhub.websocket.chat;

import com.application.friendhub.Entity.MessagesEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.MessageRepository;
import com.application.friendhub.Repository.PrivateChatRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.service.EncodedImageService;
import com.application.friendhub.loggedUser.service.MessagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@Slf4j

public class ChatController {
    private EncodedImageService encodedImageService;
    private UserRepository userRepository;
    private  AvailableUserService availableUserService;
    private MessageRepository messageRepository;
     private PrivateChatRepository privateChatRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private MessagesService messagesService;

    /*private final RabbitMQProducer rabbitMQProducer;
*/
    public ChatController(EncodedImageService encodedImageService, UserRepository userRepository, AvailableUserService availableUserService, MessageRepository messageRepository, PrivateChatRepository privateChatRepository, SimpMessagingTemplate messagingTemplate, MessagesService messagesService) {
        this.encodedImageService = encodedImageService;
        this.userRepository = userRepository;
        this.availableUserService = availableUserService;
        this.messageRepository = messageRepository;
        this.privateChatRepository = privateChatRepository;
        this.messagingTemplate = messagingTemplate;

        this.messagesService = messagesService;
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
         log.error("to jest id "+userId);
            UserEntity user = userRepository.findUserEntityByEmail(authentication.getName()).orElseThrow();
            UserEntity receiver = userRepository.findById(Long.parseLong(userId)).orElseThrow();
            message.setSender(user.getUserDetailsEntity().getFirstName() + " " + user.getUserDetailsEntity().getLastName());
            message.setDateOfSentMessage(new Date().toString());
            message.setSenderEncodedPicture(encodedImageService.encodedImage(user.getUserDetailsEntity()));
            MessagesEntity messagesEntity = messagesService.sendMessage(user, receiver, message);



              message.setDateOfSentMessage(displayFormattedDate(message.getDateOfSentMessage()));
            messagingTemplate.convertAndSend("/queue/messages/user/" + userId, message);
            /*rabbitMQProducer.sendMessage(userId,message,authentication);*/
            messageRepository.save(messagesEntity);
            return message;
        }
public String displayFormattedDate(String message){
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    LocalDateTime dateTime = LocalDateTime.parse(message, inputFormatter);
    return dateTime.format(outputFormatter);}






    @MessageMapping("/chat.requestActiveUsers")
    public void handleRequestActiveUsers() {

        HashMap<Long, String> onlineUsers = availableUserService.getOnlineUsers();

        List<Long> allActiveUsers = new ArrayList<>(onlineUsers.keySet());

        messagingTemplate.convertAndSend("/topic/public/ActiveUsersAtStart", allActiveUsers);

    }



}


