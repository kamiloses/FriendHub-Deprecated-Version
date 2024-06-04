package com.application.friendhub.loggedUser.dto;

import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.registrationProcess.other.DateOfBirth;
import com.application.friendhub.registrationProcess.other.Sex;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class UserDetailsDto {
    private Long id;
    private String firstName;
    private String lastName;
    private DateOfBirth date;
    private String localization;
    private String education;
    private String work;
    private Sex sex;
    private String interests;
    private String encodedProfilePicture;
    private UserEntity userEntity;
}
