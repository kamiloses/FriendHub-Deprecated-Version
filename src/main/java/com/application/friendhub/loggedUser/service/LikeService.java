package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.LikesEntity;
import com.application.friendhub.Entity.TimelineEntity;
import com.application.friendhub.Entity.UserDetailsEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.LikeRepository;
import com.application.friendhub.Repository.TimelineRepository;
import com.application.friendhub.Repository.UserDetailsRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.dto.LikeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j

public class LikeService {

    private LikeRepository likeRepository;
    private TimelineRepository timelineRepository;
    private UserRepository userRepository;

    public LikeService(LikeRepository likeRepository, TimelineRepository timelineRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.timelineRepository = timelineRepository;
        this.userRepository = userRepository;
    }

    public LikesEntity likeDtoToEntity(LikeDto likeDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new RuntimeException("User Not Found"));
        TimelineEntity timelineEntity = timelineRepository.findById(likeDto.getLikesId()).orElseThrow(() -> new RuntimeException("Timeline Not Found"));

        LikesEntity likesEntity = new LikesEntity();
        likesEntity.setFirstName(user.getUserDetailsEntity().getFirstName());
        likesEntity.setLastName(user.getUserDetailsEntity().getLastName());
        likesEntity.setLikeEntity(timelineEntity);
        likesEntity.setUserEntity(user);
        return likesEntity;
    }


    public boolean isPostLikedByUser(Long postId, Long userId) {
        return likeRepository.existsByLikeEntityIdAndId(postId, userId);
    }

    public void removeLike(Long postId, Long userId) {
        likeRepository.deleteByLikeEntityIdAndId(postId, userId);
    }


    public HashMap<Long, Boolean> isCommentLiked(List<TimelineEntity> allPosts, List<LikesEntity> likesEntities) {

        HashMap<Long, Boolean> yourLikes = new HashMap<>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new RuntimeException("User Not Found"));
        for (TimelineEntity post : allPosts) {
            boolean isLiked = false;
            for (LikesEntity like : likesEntities) {
                if (post.getId().equals(like.getLikeEntity().getId()) & like.getUserEntity().getId().equals(user.getId())) {
                    isLiked = true;
                    yourLikes.put(post.getId(), isLiked);
                }
            }
        }
        log.error(yourLikes.toString());


        return yourLikes;
    }
}


