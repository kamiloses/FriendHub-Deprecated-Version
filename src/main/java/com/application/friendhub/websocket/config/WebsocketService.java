package com.application.friendhub.websocket.config;

import com.application.friendhub.Entity.MessagesEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.service.EncodedImageService;
import com.application.friendhub.loggedUser.service.MessagesService;
import com.application.friendhub.websocket.dto.ChatMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Service
public class WebsocketService {

private final UserRepository userRepository;
private final EncodedImageService encodedImageService;
private final MessagesService messagesService;

    public WebsocketService(UserRepository userRepository, EncodedImageService encodedImageService, MessagesService messagesService) {
        this.userRepository = userRepository;
        this.encodedImageService = encodedImageService;
        this.messagesService = messagesService;
    }


    public MessagesEntity setMessage(String userId, ChatMessage message, UserEntity user) {



        UserEntity receiver = userRepository.findById(Long.parseLong(userId)).orElseThrow();
        message.setSender(user.getUserDetailsEntity().getFirstName() + " " + user.getUserDetailsEntity().getLastName());
        message.setDateOfSentMessage(new Date().toString());
        message.setSenderEncodedPicture(encodedImageService.encodedImage(user.getUserDetailsEntity().getProfilePicture()));

        if (message.getIsPublicChat().equals("false")) {
            MessagesEntity messagesEntity = messagesService.sendPrivateMessage(user, receiver, message);
            message.setDateOfSentMessage(displayFormattedDate(message.getDateOfSentMessage()));
            return messagesEntity;
        } else {
            return

                    messagesService.sendPublicMessage(user, receiver, message,user);
        }
    }
    public String displayFormattedDate(String message) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        LocalDateTime dateTime = LocalDateTime.parse(message, inputFormatter);
        return dateTime.format(outputFormatter);
    }

}
