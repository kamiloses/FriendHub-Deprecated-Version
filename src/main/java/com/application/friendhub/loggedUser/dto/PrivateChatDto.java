package com.application.friendhub.loggedUser.dto;

import com.application.friendhub.Entity.FriendsListEntity;
import com.application.friendhub.Entity.MessagesEntity;
import com.application.friendhub.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrivateChatDto {





    private Long id;


    private UserDto user1;


    private UserDto user2;


    private List<MessagesDTO> messages;



    private FriendsListEntity addedFriend_id;


    private FriendsListEntity addingFriend_id;




}













