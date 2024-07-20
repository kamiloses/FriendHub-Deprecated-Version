package com.application.friendhub.loggedUser.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FriendToGroupDto {
    private Long friendId;
    private Long GroupId;

}
