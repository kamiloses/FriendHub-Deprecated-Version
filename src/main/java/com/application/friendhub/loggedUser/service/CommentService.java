package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.CommentsEntity;
import com.application.friendhub.Entity.TimelineEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.CommentsRepository;
import com.application.friendhub.Repository.TimelineRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.dto.CommentDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Service
public class CommentService {


    private final UserRepository userRepository;
    private final TimelineRepository timelineRepository;
    private final CommentsRepository commentsRepository;

    public CommentService(UserRepository userRepository, TimelineRepository timelineRepository, CommentsRepository commentsRepository) {
        this.userRepository = userRepository;
        this.timelineRepository = timelineRepository;
        this.commentsRepository = commentsRepository;
    }

    public CommentsEntity commentDtoToEntity(CommentDto commentDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new RuntimeException("User Not Found"));
        TimelineEntity timelineEntity = timelineRepository.findById(commentDto.getTimelineId()).orElseThrow(() -> new RuntimeException("Post Not Found"));
        CommentsEntity commentsEntity = new CommentsEntity();
        commentsEntity.setFirstName(user.getUserDetailsEntity().getFirstName());
        commentsEntity.setLastName(user.getUserDetailsEntity().getLastName());
        commentsEntity.setComment(commentDto.getComment());
        commentsEntity.setDateOfPublication(new Date());
        commentsEntity.setTimelineEntity(timelineEntity);
        commentsEntity.setUserEntity(user);
        return commentsEntity;
    }


    public void addReplyToComment(Long parentCommentId, String content, UserEntity user) {
        CommentsEntity parentComment = commentsRepository.findById(parentCommentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));
        TimelineEntity timeline = parentComment.getTimelineEntity();

        CommentsEntity newComment = new CommentsEntity();
        newComment.setComment(content);
        newComment.setParentComment(parentComment);
        newComment.setTimelineEntity(timeline);
        newComment.setUserEntity(user);
        newComment.setDateOfPublication(new Date());


        commentsRepository.save(newComment);
    }


    public void addReply(CommentDto commentDto, Long parentCommentId) {
        CommentsEntity parentComment = commentsRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        CommentsEntity reply = commentDtoToEntity(commentDto);
        reply.setParentComment(parentComment);
        reply.setTimelineEntity(parentComment.getTimelineEntity());
        commentsRepository.save(reply);
    }



    public void addComment(CommentDto commentDto){

        CommentsEntity commentsEntity =commentDtoToEntity(commentDto);
        commentsRepository.save(commentsEntity);
    }
}
