package com.application.friendhub.loggedUser.dto;

import com.application.friendhub.registrationProcess.other.DateOfBirth;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FriendsDto {



    private Long id;
    private String firstName;
    private String lastName;
    private DateOfBirth friendshipDate;
    private Long userId;




}
