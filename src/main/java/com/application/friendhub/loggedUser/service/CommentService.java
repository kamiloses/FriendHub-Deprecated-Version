package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.CommentsEntity;
import com.application.friendhub.Entity.TimelineEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.TimelineRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.dto.CommentDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CommentService {


   private UserRepository userRepository;
   private TimelineRepository timelineRepository;

    public CommentService(UserRepository userRepository, TimelineRepository timelineRepository) {
        this.userRepository = userRepository;
        this.timelineRepository = timelineRepository;
    }

    public CommentsEntity commentDtoToEntity(CommentDto commentDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new RuntimeException("User Not Found"));
        TimelineEntity timelineEntity = timelineRepository.findById(commentDto.getTimelineId()).orElseThrow(() -> new RuntimeException("Timeline Not Found"));
        CommentsEntity commentsEntity = new CommentsEntity();
        commentsEntity.setFirstName(user.getUserDetailsEntity().getFirstName());
        commentsEntity.setLastName(user.getUserDetailsEntity().getLastName());
        commentsEntity.setComment(commentDto.getComment());
       commentsEntity.setDateOfPublication(new Date());
       commentsEntity.setCommentsEntity(timelineEntity); //todo ustaw

       return commentsEntity;
    }






}
