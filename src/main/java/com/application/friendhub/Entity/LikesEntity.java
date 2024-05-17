package com.application.friendhub.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class LikesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;


    @ManyToOne
    TimelineEntity likeEntity;


    @ManyToOne
    UserEntity userEntity;


}
