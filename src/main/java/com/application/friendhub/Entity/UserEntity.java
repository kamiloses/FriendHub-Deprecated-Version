package com.application.friendhub.Entity;

import com.application.friendhub.dto.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy ="userId" )
    private List<FriendsListEntity> friendsListEntities;


    @OneToMany(mappedBy = "user")
    private List<TimelineEntity> timelines;



//todo zaimplementować poten interface userDetails i zaimplementować is non expired itp
   /* @OneToOne(mappedBy = "userEntity")
    @OneToMany(mappedBy = "userEntity")
    private UserDetailsEntity userDetailsEntity;
    private List<MessagesEntity> message;
*/

}


