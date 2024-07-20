package com.application.friendhub.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FriendsListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Date friendshipDate;


        @ManyToOne
        @JoinColumn(name = "user_entity_id")
        private UserEntity userEntity;


        @ManyToOne
    private UserEntity connectionToYourOwnAccount;


    @OneToOne(mappedBy ="addedFriend_id")
    private PrivateChatEntity addedFriend;

    @OneToOne(mappedBy = "addingFriend_id")
    private PrivateChatEntity addingFriend;




}

