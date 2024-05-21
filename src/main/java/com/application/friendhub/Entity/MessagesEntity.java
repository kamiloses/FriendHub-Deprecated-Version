package com.application.friendhub.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.Date;

@Entity

@Getter
@Setter
@NoArgsConstructor
public class MessagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date DateOfSentMessage;

    private String contextOfReceivedMessage;


    private Date dateOfReceivedMessage;

    @ManyToOne
    private UserEntity uploader ;


    @ManyToOne
    private UserEntity recipient;





}
