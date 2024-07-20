package com.application.friendhub.api.service;

import com.application.friendhub.Entity.CommentsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.CommentsRepository;
import com.application.friendhub.Repository.LikeRepository;
import com.application.friendhub.Repository.TimelineRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.api.dto.CommentsApiDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApiService {

    private final UserRepository userRepository;
    private final TimelineRepository timelineRepository;
    private final CommentsRepository commentsRepository;
    private final LikeRepository likeRepository;

    public ApiService(UserRepository userRepository, TimelineRepository timelineRepository, CommentsRepository commentsRepository, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.timelineRepository = timelineRepository;
        this.commentsRepository = commentsRepository;
        this.likeRepository = likeRepository;
    }

    public int userActivity(String userEmail,int days) {
         UserEntity user = userRepository.findUserEntityByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User was Not found"));
         LocalDateTime localDateTime = LocalDateTime.now().minusDays(days);
         int size1 = timelineRepository.findTimelineEntitiesByUserIdInSpecificTime(user.getId(), localDateTime).size();
         int size2 = commentsRepository.findCommentsEntitiesByUserIdInSpecificTime(user.getId(), localDateTime).size();
         int size3 = likeRepository.findLikesEntitiesByUserIdInSpecificTime(user.getId(), localDateTime).size();
     return size1 + size2 + size3;}



    public List<CommentsApiDto> commentEntityToApiDto(List<CommentsEntity> userComments) {
        return     userComments.stream().map(comments->{
            CommentsApiDto commentsApiDto = new CommentsApiDto();
            commentsApiDto.setId(comments.getId());
            commentsApiDto.setComment(comments.getComment());
            commentsApiDto.setDateOfPublication(comments.getDateOfPublication().toString());
            commentsApiDto.setReferencedUser(comments.getTimelineEntity().getAuthor());
            return commentsApiDto;}).toList();




    }

    public List<CommentsApiDto> allUserComments(String userEmail){
        UserEntity userEntity = userRepository.findUserEntityByEmail(userEmail).orElseThrow(() -> new UsernameNotFoundException("User was Not found"));
        List<CommentsEntity> allUserCommentsEntity = userEntity.getCommentEntity();
        return commentEntityToApiDto(allUserCommentsEntity);
    }


}
