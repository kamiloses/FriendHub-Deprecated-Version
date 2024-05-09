package com.application.friendhub.Entity.mainSide;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class TimelineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private String author;


    private String post;

    private String image;

    private Date dateOfPublication;

    @ManyToMany(mappedBy = "timelineEntitiesFromLikes")
    private List<LikesEntity> likesEntity;

    @ManyToMany(mappedBy = "timelineEntitiesFromComments")
    private List<CommentsEntity> commentsEntity;


}
