package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.MessagesEntity;
import com.application.friendhub.Entity.PrivateChatEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.loggedUser.dto.MessagesDTO;
import com.application.friendhub.loggedUser.dto.PrivateChatDto;
import com.application.friendhub.loggedUser.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateChatService {

private UserDetailsDtoService userDetailsDtoService;
private MessagesService messagesService;
private UserService userService;
    public PrivateChatService(UserDetailsDtoService userDetailsDtoService, MessagesService messagesService, UserService userService) {
        this.userDetailsDtoService = userDetailsDtoService;
        this.messagesService = messagesService;
        this.userService = userService;
    }

    public List<PrivateChatDto> privateChatEntityToDto(List<PrivateChatEntity> privateChatEntities) {
        return privateChatEntities.stream().map(privateChatEntity -> {
            PrivateChatDto privateChatDto = new PrivateChatDto();
            privateChatDto.setId(privateChatEntity.getId());
            privateChatDto.setUser1(userService.userEntityToDto(privateChatEntity.getUser1()));
            privateChatDto.setUser2(userService.userEntityToDto(privateChatEntity.getUser1()));
            privateChatDto.setMessages(messagesService.messagesEntityToDto(privateChatEntity.getMessages()));
            privateChatDto.setAddedFriend_id(privateChatEntity.getAddedFriend_id());
            privateChatDto.setAddingFriend_id(privateChatEntity.getAddingFriend_id());


            return privateChatDto;
        }).toList();


    }

/*

    protected PrivateChatDto privateChatEntityToDto(PrivateChatEntity privateChatEntity) {
        PrivateChatDto privateChatDto = new PrivateChatDto();
        privateChatDto.setId(privateChatEntity.getId());
        privateChatDto.setUser1(userService.userEntityToDto(privateChatEntity.getUser1()));
        privateChatDto.setUser2(userService.userEntityToDto(privateChatEntity.getUser1()));
        privateChatDto.setMessages(messagesService.messagesEntityToDto(privateChatEntity.getMessages()));
        privateChatDto.setAddedFriend_id(privateChatEntity.getAddedFriend_id());
        privateChatDto.setAddingFriend_id(privateChatEntity.getAddingFriend_id());

   return privateChatDto; }

*/




}