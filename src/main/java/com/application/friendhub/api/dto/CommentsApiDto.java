package com.application.friendhub.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentsApiDto {


private Long id;
private String comment;
private String dateOfPublication;
private String referencedUser;

}
