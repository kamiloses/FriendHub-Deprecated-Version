package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.FriendsListEntity;
import com.application.friendhub.Entity.PrivateChatEntity;
import com.application.friendhub.Repository.FriendsListRepository;
import com.application.friendhub.Repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrivateChatService {

    private final UserDetailsDtoService userDetailsDtoService;
    private final MessagesService messagesService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final FriendsListRepository friendsListRepository;

    public PrivateChatService(UserDetailsDtoService userDetailsDtoService, MessagesService messagesService, UserService userService, UserRepository userRepository, FriendsListRepository friendsListRepository) {
        this.userDetailsDtoService = userDetailsDtoService;
        this.messagesService = messagesService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.friendsListRepository = friendsListRepository;
    }

   /* public List<PrivateChatDto> privateChatEntityToDto(List<PrivateChatEntity> privateChatEntities) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow();
        return privateChatEntities.stream().map(privateChatEntity -> {
            PrivateChatDto privateChatDto = new PrivateChatDto();
            privateChatDto.setId(privateChatEntity.getId());
            privateChatDto.setUser1(userService.userEntityToDto(privateChatEntity.getUser1()));
            privateChatDto.setUser2(userService.userEntityToDto(privateChatEntity.getUser1()));
            privateChatDto.setMessages(messagesService.messagesEntityToDto(privateChatEntity.getMessages()));
            privateChatDto.setAddedFriend_id(privateChatEntity.getAddedFriend_id());
            privateChatDto.setAddingFriend_id(privateChatEntity.getAddingFriend_id());
             privateChatDto.setYourAccountId(user.getId());

            return privateChatDto;
        }).toList();


    }*/

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


    public PrivateChatEntity createPrivateChatService(FriendsListEntity invitedFriendsListEntity, FriendsListEntity invitingFriendsEntity) {
        FriendsListEntity friendsListEntity = friendsListRepository.findById(invitedFriendsListEntity.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        FriendsListEntity friendsListEntity1 = friendsListRepository.findById(invitingFriendsEntity.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        PrivateChatEntity privateChatEntity = new PrivateChatEntity();

        privateChatEntity.setUser1(friendsListEntity.getUserEntity());
        privateChatEntity.setUser2(friendsListEntity1.getUserEntity());
        privateChatEntity.setAddedFriend_id(invitedFriendsListEntity);
        privateChatEntity.setAddingFriend_id(invitingFriendsEntity);
        return privateChatEntity;
    }


}