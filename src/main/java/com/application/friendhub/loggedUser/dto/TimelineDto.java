package com.application.friendhub.loggedUser.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class TimelineDto {


    private Long id;

    private String author;

    private String uploadedUser;


    private String post;

    private MultipartFile multipartFile;

    private Date dateOfPublication;

    private Long user;
}
