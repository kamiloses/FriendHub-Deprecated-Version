package com.application.friendhub.websocket.chat;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatMessage /*implements Serializable*/ {

/*
    private static final long serialVersionUID = 1L;
*/


    private String content;
    private String sender;
    private String senderEncodedPicture;
    private String dateOfSentMessage;
    private String receiver;
    private String receiverEncodedPicture;
    private Date dateOfReceivedMessage;

}
