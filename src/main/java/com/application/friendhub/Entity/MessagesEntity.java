package com.application.friendhub.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity

public class MessagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date DateOfSentMessage;

    private String contextOfReceivedMessage;


    private Date dateOfReceivedMessage;
    @ManyToOne
    private UserEntity userEntity;


}
