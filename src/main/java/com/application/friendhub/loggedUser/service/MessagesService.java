package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.MessagesEntity;
import com.application.friendhub.Entity.PrivateChatEntity;
import com.application.friendhub.Entity.PublicChatEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.PrivateChatRepository;
import com.application.friendhub.Repository.PublicChatRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.dto.MessagesDTO;
import com.application.friendhub.websocket.dto.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessagesService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PrivateChatRepository privateChatRepository;
    private final PublicChatRepository publicChatRepository;

    public MessagesService(UserService userService, PrivateChatRepository privateChatRepository, UserRepository userRepository, PublicChatRepository publicChatRepository) {
        this.userService = userService;

        this.privateChatRepository = privateChatRepository;
        this.userRepository = userRepository;
        this.publicChatRepository = publicChatRepository;
    }

    public List<MessagesDTO> messagesEntityToDto(List<MessagesEntity> entity) {
        return entity.stream().map(messagesEntity -> {
            MessagesDTO dto = new MessagesDTO();
            dto.setId(messagesEntity.getId());
            dto.setDateOfSentMessage(messagesEntity.getDateOfSentMessage());
            dto.setContextOfReceivedMessage(messagesEntity.getContextOfReceivedMessage());
            dto.setSender(userService.userEntityToDto(messagesEntity.getSender()));
            dto.setPrivateChats_id(messagesEntity.getPrivateChats_id());
            dto.setPublicChats_id(messagesEntity.getPublicChats_id());
            return dto;
        }).toList();
    }

    public MessagesEntity sendPrivateMessage(UserEntity sender, UserEntity receiver, ChatMessage message) {
        UserEntity userEntity = userRepository.findById(sender.getId()).orElseThrow();
        UserEntity userEntity1 = userRepository.findById(receiver.getId()).orElseThrow();
        MessagesEntity messagesEntity = new MessagesEntity();
        messagesEntity.setDateOfSentMessage(new Date());
        messagesEntity.setContextOfReceivedMessage(message.getContent());
        messagesEntity.setSender(userEntity);
        messagesEntity.setPrivateChats_id(mutualChat(userEntity, userEntity1));

        return messagesEntity;
    }


    public MessagesEntity sendPublicMessage(UserEntity sender, UserEntity receiver, ChatMessage message, UserEntity user) {
        PublicChatEntity publicChatEntity = publicChatRepository.findById(message.getChatId()).orElseThrow();
        MessagesEntity messagesEntity = new MessagesEntity();
        messagesEntity.setDateOfSentMessage(new Date());
        messagesEntity.setContextOfReceivedMessage(message.getContent());
        messagesEntity.setSender(sender);
        messagesEntity.setPublicChats_id(publicChatEntity);

        return messagesEntity;
    }


    private PrivateChatEntity mutualChat(UserEntity sender, UserEntity receiver) {
        return privateChatRepository.findPrivateChatEntitiesByUser1_IdAndUser2_Id(sender.getId(), receiver.getId());

    }


}
