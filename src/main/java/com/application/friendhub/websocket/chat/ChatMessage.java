package com.application.friendhub.websocket.chat;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ChatMessage {


    private String content;
    private String sender;
    private MessageType type;
    private String recipientId;
}
