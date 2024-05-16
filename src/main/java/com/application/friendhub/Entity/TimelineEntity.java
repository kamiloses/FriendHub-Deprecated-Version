package com.application.friendhub.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimelineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;

    @Column(length = 1337)
    private String post;

    private String image;

    private Date dateOfPublication;

    @ManyToOne
    private UserEntity user;





    @ManyToMany(mappedBy = "likesEntity")
    @Column(name = "like_id")
    private List<LikesEntity> likesEntities;

    @ManyToMany(mappedBy = "commentsEntity")
    @Column(name = "comments_id" )
    private List<CommentsEntity> commentsEntity;



}
