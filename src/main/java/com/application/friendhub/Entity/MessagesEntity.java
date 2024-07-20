package com.application.friendhub.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MessagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateOfSentMessage;
    private String contextOfReceivedMessage;

    @ManyToOne
    private UserEntity sender;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    private List<MessageRecipientEntity> recipients;

    @ManyToOne
    @JoinColumn(name = "private_chat_id")
    private PrivateChatEntity privateChats_id;

    @ManyToOne
    @JoinColumn(name = "public_chat_id")
    private PublicChatEntity publicChats_id;
}
