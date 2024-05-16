package com.application.friendhub.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class FriendsListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Date friendshipDate;


        @ManyToOne

        private UserEntity userId;


        @ManyToOne
    private UserEntity connectionToYourOwnAccount;

}
