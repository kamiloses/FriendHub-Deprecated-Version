package com.application.friendhub.loggedUser.dto;

import com.application.friendhub.Entity.TimelineEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class CommentDto {



    private String firstName;

    private String lastName;

    private String comment;

    private Date dateOfPublication;

   private Long timelineId;


}





