package com.application.friendhub.loggedUser.dto;

import com.application.friendhub.Entity.PrivateChatEntity;
import com.application.friendhub.Entity.PublicChatEntity;
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



    private UserDto sender;


    private MessageRecipientDto MessageRecipientDto ;


    private PrivateChatEntity privateChats_id;
    private PublicChatEntity publicChats_id;


}
