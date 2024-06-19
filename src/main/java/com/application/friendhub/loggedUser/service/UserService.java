package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.loggedUser.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDetailsDtoService userDetailsDtoService;

    public UserService(UserDetailsDtoService userDetailsDtoService) {
        this.userDetailsDtoService = userDetailsDtoService;
    }

    protected UserDto userEntityToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPassword(userEntity.getPassword());
        userDto.setRole(userEntity.getRole());
        userDto.setFriendsListEntities(userEntity.getFriendsListEntities());
        userDto.setTimelines(userEntity.getTimelines());
        userDto.setConnectionToYourOwnAccount(userEntity.getConnectionToYourOwnAccount());
        // Mapowanie UserDetailsDto wymaga stworzenia mapera dla UserDetailsEntity
        userDto.setUserDetailsDto(userDetailsDtoService.userDetailsEntityToDto(userEntity.getUserDetailsEntity()));
        userDto.setLikesEntities(userEntity.getLikesEntities());
        userDto.setCommentEntity(userEntity.getCommentEntity());
        userDto.setPrivateChatUserOne_id(userEntity.getPrivateChatUserOne_id());
        userDto.setPrivateChatUserTwo_id(userEntity.getPrivateChatUserTwo_id());
        return userDto;
    }

}
