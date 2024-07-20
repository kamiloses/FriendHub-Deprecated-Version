package com.application.friendhub.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CommentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String comment;

    private Date dateOfPublication;

    @ManyToOne

    private TimelineEntity timelineEntity;



    @ManyToOne
    UserEntity userEntity;





    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private CommentsEntity parentComment;


    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<CommentsEntity> replies = new ArrayList<>();


}






