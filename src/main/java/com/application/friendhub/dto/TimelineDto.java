package com.application.friendhub.dto;

import com.application.friendhub.Entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;
@Data
public class TimelineDto {


    private String author;

    private String post;

    private String image;

    private Date dateOfPublication;

    private Long user;
}
