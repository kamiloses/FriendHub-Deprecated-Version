package com.application.friendhub.loggedUser.dto;

import lombok.Data;

import java.util.Date;
@Data
public class CommentDto {



    private String firstName;

    private String lastName;

    private String comment;

    private Date dateOfPublication;

   private Long timelineId;

   private Long userEntityId;

}





