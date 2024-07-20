package com.application.friendhub.loggedUser.dto;

import com.application.friendhub.Entity.PrivateChatEntity;
import com.application.friendhub.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FriendsListDto {

    private Long id;
    private String firstName;
    private String lastName;
    private Date friendshipDate;



    private UserDto userId;


    private UserDto connectionToYourOwnAccount;


    private PrivateChatEntity addedFriend_id;

    private PrivateChatEntity addingFriend_id;



    private List<Long> possibleGroupToInvite; //
}
