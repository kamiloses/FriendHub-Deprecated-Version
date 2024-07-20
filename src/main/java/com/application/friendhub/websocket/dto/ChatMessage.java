package com.application.friendhub.websocket.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatMessage  {


    private Long chatId;
    private String content;
    private String sender;
    private String senderEncodedPicture;
    private String dateOfSentMessage;
    private String receiver;
    private String receiverEncodedPicture;
    private String dateOfReceivedMessage;
    private String isPublicChat;
}
