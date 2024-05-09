package com.application.friendhub.Entity.mainSide;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class CommentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String comment;

    private Date dateOfPublication;

    @ManyToMany
    private List<TimelineEntity> timelineEntitiesFromComments;


}
