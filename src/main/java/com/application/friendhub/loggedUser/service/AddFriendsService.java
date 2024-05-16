package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.FriendsListEntity;
import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.FriendsListRepository;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.dto.FriendsDto;
import com.application.friendhub.registrationProcess.other.DateOfBirth;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AddFriendsService {

    private final UserDetailsRepository userDetailsRepository;
    private FriendsListRepository friendsListRepository;
private UserRepository userRepository;

    public AddFriendsService(FriendsListRepository friendsListRepository, UserRepository userRepository, UserDetailsRepository userDetailsRepository) {
        this.friendsListRepository = friendsListRepository;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

      public FriendsListEntity invitedFriendsDtoToEntity(FriendsDto friendsDto) {
          UserEntity userEntity = userRepository.findUserEntityById(friendsDto.getId());
          String name = SecurityContextHolder.getContext().getAuthentication().getName();
          UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new UsernameNotFoundException("User not found"));
          FriendsListEntity friendsListEntity = new FriendsListEntity();
        friendsListEntity.setFirstName(friendsDto.getFirstName());
        friendsListEntity.setLastName(friendsDto.getLastName());
        friendsListEntity.setFriendshipDate(new Date());
          friendsListEntity.setUserId(user);
          friendsListEntity.setConnectionToYourOwnAccount(userEntity);




     return friendsListEntity; }



/*
    public FriendsDto invitedFriendsEntityToDto(FriendsListEntity friendsListEntity,UserEntity user) {
        FriendsDto friendsDto = new FriendsDto();
        friendsDto.setFirstName(friendsListEntity.getFirstName());
        friendsDto.setLastName(friendsListEntity.getLastName());
        friendsDto.setFriendshipDate(new DateOfBirth());

    return friendsDto;}
*/








      public FriendsListEntity invitingFriendsDtoToEntity(FriendsDto friendsDto){
          String name = SecurityContextHolder.getContext().getAuthentication().getName();
          UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new UsernameNotFoundException("not found"));
          UserDetailsEntity userDetails = userDetailsRepository.findUserDetailsEntityByUserEntity_Email(name);
          UserEntity userEntity = userRepository.findUserEntityById(friendsDto.getId());
          FriendsListEntity friendsListEntity = new FriendsListEntity();
           friendsListEntity.setFirstName(userDetails.getFirstName());
           friendsListEntity.setLastName(userDetails.getLastName());
          friendsListEntity.setFriendshipDate(new Date());
          friendsListEntity.setUserId(userEntity);
          friendsListEntity.setConnectionToYourOwnAccount(user);
          return friendsListEntity;}






}
