package com.application.friendhub.Entity;

import com.application.friendhub.api.other.Role;
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
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    private Date createdAt;
    private Date lastActivity;

    @OneToMany(mappedBy = "userEntity")
    private List<FriendsListEntity> friendsListEntities;

    @OneToMany(mappedBy = "user")
    private List<TimelineEntity> timelines;

    @OneToMany(mappedBy = "connectionToYourOwnAccount")
    private List<FriendsListEntity> connectionToYourOwnAccount;

    @OneToOne(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
    private UserDetailsEntity userDetailsEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
    private List<LikesEntity> likesEntities;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
    private List<CommentsEntity> commentEntity;

    @OneToMany(mappedBy = "user1")
    private List<PrivateChatEntity> privateChatUserOne_id;

    @OneToMany(mappedBy = "user2")
    private List<PrivateChatEntity> privateChatUserTwo_id;

    @ManyToMany(mappedBy = "userEntity")
    private List<PublicChatEntity> publicChats;

    @OneToMany(mappedBy = "recipient")
    private List<MessageRecipientEntity> receivedMessages;
}
