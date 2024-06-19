package com.application.friendhub.loggedUser.dto;

import com.application.friendhub.Entity.*;
import com.application.friendhub.api.other.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {







        private Long id;

        @NonNull
        private String email;
        @NonNull
        private String password;
        @NonNull
        @Enumerated(EnumType.STRING)
        private Role role;

        private List<FriendsListEntity> friendsListEntities;


        private List<TimelineEntity> timelines;

        private List<FriendsListEntity> connectionToYourOwnAccount;


        private UserDetailsDto userDetailsDto;


        private List<LikesEntity> likesEntities;


        private List<CommentsEntity> commentEntity;



        private List<PrivateChatEntity> privateChatUserOne_id; ;


        private List<PrivateChatEntity> privateChatUserTwo_id ;



    }



