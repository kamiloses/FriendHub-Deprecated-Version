package com.application.friendhub.Entity.mainSide;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class LikesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

     @ManyToMany
    private List<TimelineEntity> timelineEntitiesFromLikes;



}
