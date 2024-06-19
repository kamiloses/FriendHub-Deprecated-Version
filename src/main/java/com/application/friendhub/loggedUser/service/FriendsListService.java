package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.FriendsListEntity;
import com.application.friendhub.loggedUser.dto.FriendsListDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsListService {



private UserService userService;

    public FriendsListService(UserService userService) {
        this.userService = userService;
    }


    public List<FriendsListDto> friendsListEntityToDto(List<FriendsListEntity> friendsListEntities){
    return     friendsListEntities.stream().map(friendsListEntity -> {
            FriendsListDto friendsListDto = new FriendsListDto();
            friendsListDto.setId(friendsListEntity.getId());
            friendsListDto.setFirstName(friendsListEntity.getFirstName());
            friendsListDto.setLastName(friendsListEntity.getLastName());
            friendsListDto.setFriendshipDate(friendsListEntity.getFriendshipDate());
            friendsListDto.setUserId(userService.userEntityToDto(friendsListEntity.getUserId()));
            friendsListDto.setConnectionToYourOwnAccount(userService.userEntityToDto(friendsListEntity.getConnectionToYourOwnAccount()));
            friendsListDto.setAddedFriend_id(friendsListEntity.getAddedFriend_id());
            friendsListDto.setAddingFriend_id(friendsListEntity.getAddingFriend_id());

return friendsListDto;
}).toList();

    }













}
