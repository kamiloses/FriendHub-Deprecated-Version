package com.application.friendhub.loggedUser.service;

import com.application.friendhub.Entity.LikesEntity;
import com.application.friendhub.Entity.TimelineEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Repository.LikeRepository;
import com.application.friendhub.Repository.TimelineRepository;
import com.application.friendhub.Repository.UserRepository;
import com.application.friendhub.loggedUser.dto.LikeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j

public class LikeService {

    private final LikeRepository likeRepository;
    private final TimelineRepository timelineRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository, TimelineRepository timelineRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.timelineRepository = timelineRepository;
        this.userRepository = userRepository;
    }

    public LikesEntity likeDtoToEntity(LikeDto likeDto) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).get();
        TimelineEntity timelineEntity = timelineRepository.findById(likeDto.getLikesId()).get();

        LikesEntity likesEntity = new LikesEntity();
        likesEntity.setFirstName(user.getUserDetailsEntity().getFirstName());
        likesEntity.setLastName(user.getUserDetailsEntity().getLastName());
        likesEntity.setDateOfLike(new Date());
        likesEntity.setTimelineEntity(timelineEntity);
        likesEntity.setUserEntity(user);
        return likesEntity;
    }


    public boolean isPostLikedByUser(Long postId, Long userId) {
        return likeRepository.existsByTimelineEntityIdAndId(postId, userId);
    }

    public void removeLike(Long postId, Long userId) {
        likeRepository.deleteByTimelineEntityIdAndId(postId, userId);
    }


    public HashMap<Long, Boolean> isCommentLiked(List<TimelineEntity> allPosts, List<LikesEntity> likesEntities) {

        HashMap<Long, Boolean> yourLikes = new HashMap<>();
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findUserEntityByEmail(name).orElseThrow(() -> new RuntimeException("User Not Found"));
        for (TimelineEntity post : allPosts) {
            boolean isLiked = false;//todo popraw implementacje
            for (LikesEntity like : likesEntities) {
                if (post.getId().equals(like.getTimelineEntity().getId()) & like.getUserEntity().getId().equals(user.getId())) {
                    isLiked = true;
                    yourLikes.put(post.getId(), isLiked);
                }
            }
        }
        log.error(yourLikes.toString());


        return yourLikes;
    }
public void addLike(LikeDto likeDto){

    String myEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    UserEntity myAccount = userRepository.findUserEntityByEmail(myEmail).get();

    if (likeRepository.existsByTimelineEntityIdAndUserEntityId(likeDto.getLikesId(), myAccount.getId()) && userRepository.existsById(myAccount.getId())) {
        LikesEntity likesEntity = likeRepository.findByTimelineEntity_IdAndUserEntity_Id(likeDto.getLikesId(), myAccount.getId());
        likeRepository.delete(likesEntity);

    } else {
        LikesEntity likesEntity = likeDtoToEntity(likeDto);
        likeRepository.save(likesEntity);
    }






}
public Map<Long,Integer> allPostLikesCountMap(List<TimelineEntity> allPosts, List<LikesEntity> likesEntities) {
    Map<Long, Integer> postLikesCountMap = new HashMap<>();
    for (TimelineEntity post : allPosts) {
        int likesCount = 0;
        for (LikesEntity like : likesEntities) {
            if (like.getTimelineEntity().getId().equals(post.getId())) {
                likesCount++;
            }
        }
        postLikesCountMap.put(post.getId(), likesCount);
    }
return postLikesCountMap;
}





}


