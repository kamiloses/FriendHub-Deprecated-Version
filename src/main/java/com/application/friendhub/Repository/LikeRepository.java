package com.application.friendhub.Repository;

import com.application.friendhub.Entity.LikesEntity;
import com.application.friendhub.Entity.TimelineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LikeRepository extends JpaRepository<LikesEntity,Long> {


    boolean existsByTimelineEntityIdAndId(Long postId, Long likeId);
    void deleteByTimelineEntityIdAndId(Long postId, Long likeId);



   LikesEntity findByTimelineEntity_IdAndUserEntity_Id(Long timelineEntityId,Long UserEntityId);

   boolean existsByTimelineEntityIdAndUserEntityId(Long timelineEntityId,Long UserEntityId);


    @Query("SELECT l FROM LikesEntity l WHERE l.timelineEntity IN :timelineEntities")
    List<LikesEntity> findLikesByTimelineEntities(@Param("timelineEntities") List<TimelineEntity> timelineEntities);


    @Query("SELECT t FROM LikesEntity t WHERE t.userEntity.id = :userId AND t.dateOfLike >= :startDate")
    List<LikesEntity> findLikesEntitiesByUserIdInSpecificTime(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate);

}

