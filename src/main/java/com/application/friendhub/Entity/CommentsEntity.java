package com.application.friendhub.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class CommentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String comment;

    private Date dateOfPublication;

    @ManyToMany
    @Column(name = "timeline_id")
    @JoinTable(
            name = "comments_timeline",
            joinColumns = @JoinColumn(name ="column_id"),
            inverseJoinColumns = @JoinColumn(name = "timeline_id"))
    private List<TimelineEntity> commentsEntity;


}
