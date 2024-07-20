package com.application.friendhub.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PrivateChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private UserEntity user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private UserEntity user2;

    @OneToMany(mappedBy = "privateChats_id", cascade = CascadeType.REMOVE)
    private List<MessagesEntity> messages;

    @OneToOne
    private FriendsListEntity addedFriend_id;

    @OneToOne
    private FriendsListEntity addingFriend_id;
}
