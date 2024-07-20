package com.application.friendhub.loggedUser.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PublicChatDto {

    private Long id;


    private String groupName;

    private MultipartFile groupImage;

    private Date createdAt;


    private List<UserDto> userDto;


    private String encodedImage;


    private List<MessagesDTO> messages;






}
