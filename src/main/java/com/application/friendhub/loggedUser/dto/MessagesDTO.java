package com.application.friendhub.loggedUser.dto;

import com.application.friendhub.Entity.PrivateChatEntity;
import com.application.friendhub.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;



@Getter
@Setter
@NoArgsConstructor
@ToString

public class MessagesDTO {
    private Long id;

    private Date DateOfSentMessage;

    private String contextOfReceivedMessage;


    private Date dateOfReceivedMessage;

    private UserDto sender;


    private UserDto receiver;


    private PrivateChatEntity privateChats_id; //todo tu


}
