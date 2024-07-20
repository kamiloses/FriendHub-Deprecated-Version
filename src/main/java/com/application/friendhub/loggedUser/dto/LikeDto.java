package com.application.friendhub.loggedUser.dto;

import lombok.Data;

import java.util.Date;

@Data
public class LikeDto {


    private Long id;

    private String firstName;

    private String lastName;

     private Date  dateOfLike;

     private Long likesId;

     private Long userId;





}



