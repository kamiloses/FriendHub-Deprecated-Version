package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.CommentsEntity;
import com.application.friendhub.loggedUser.dto.CommentDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentService {


    CommentDto commentEntityToDto(CommentsEntity commentsEntity) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        CommentDto commentDto = new CommentDto();
        commentDto.setFirstName(commentsEntity.getFirstName());
        commentDto.setLastName(commentsEntity.getLastName());
        commentDto.setComment(commentsEntity.getComment());
        commentDto.setDateOfPublication(commentsEntity.getDateOfPublication());
        /*commentDto.setTimelineId(1L);*/

    return commentDto;}




}
