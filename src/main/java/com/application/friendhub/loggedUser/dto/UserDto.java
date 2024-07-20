package com.application.friendhub.loggedUser.dto;

import com.application.friendhub.Entity.*;
import com.application.friendhub.api.other.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {







        private Long id;

        private String email;
        private String password;
        @Enumerated(EnumType.STRING)
        private Role role;

        private Date createdAt;
        private Date lastActivity;
        private List<FriendsListEntity> friendsListEntities;


        private List<TimelineEntity> timelines;

        private List<FriendsListEntity> connectionToYourOwnAccount;


        private UserDetailsDto userDetailsDto;


        private List<LikesEntity> likesEntities;


        private List<CommentsEntity> commentEntity;



        private List<PrivateChatEntity> privateChatUserOne_id; ;


        private List<PrivateChatEntity> privateChatUserTwo_id ;


        private List<PublicChatEntity> publicChatUserOne_id;


    }



