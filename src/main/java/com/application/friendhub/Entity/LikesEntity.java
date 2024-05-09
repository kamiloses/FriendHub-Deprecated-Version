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

    @ManyToMany
    @Column(name = "timeline_id")
    @JoinTable(
            name = "likes_timeline",
            joinColumns = @JoinColumn(name ="like_id"),
            inverseJoinColumns = @JoinColumn(name = "timeline_id"))
    private List<TimelineEntity> likesEntity;



}
