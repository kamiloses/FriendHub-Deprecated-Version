package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.MessagesEntity;
import com.application.friendhub.loggedUser.dto.MessagesDTO;
import com.application.friendhub.loggedUser.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessagesService {
private UserService userService;

    public MessagesService(UserService userService) {
        this.userService = userService;

    }

   public List<MessagesDTO> messagesEntityToDto(List<MessagesEntity> entity) {
        return    entity.stream().map(messagesEntity ->{
            MessagesDTO dto = new MessagesDTO();
            dto.setId(messagesEntity.getId());
            dto.setDateOfSentMessage(messagesEntity.getDateOfSentMessage());
            dto.setContextOfReceivedMessage(messagesEntity.getContextOfReceivedMessage());
            dto.setDateOfReceivedMessage(messagesEntity.getDateOfReceivedMessage());
            dto.setSender(userService.userEntityToDto(messagesEntity.getSender()));
            dto.setReceiver(userService.userEntityToDto(messagesEntity.getReceiver()));
            dto.setPrivateChats_id(messagesEntity.getPrivateChats_id());
            return dto;}).toList();
    }







}
