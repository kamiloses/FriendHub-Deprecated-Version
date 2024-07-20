package com.application.friendhub.Repository;

import com.application.friendhub.Entity.TimelineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TimelineRepository extends JpaRepository<TimelineEntity, Long> {


    @Query(value = "SELECT t FROM TimelineEntity t WHERE t.author = CONCAT(:firstName, ' ', :lastName)")
    List<TimelineEntity> findTimelineEntitiesWithMatchingAuthor(String firstName, String lastName);


  /*  @Query("SELECT c FROM CommentsEntity c JOIN c.commentsEntity t WHERE t.id = :timelineId")
    List<CommentsEntity> findCommentsE(List<TimelineEntity> timelineEntity);*/

//    TimelineEntity findTimelineEntity(Long user);


    @Query(value = "SELECT t FROM TimelineEntity t WHERE t.id = (SELECT MAX(t2.id) FROM TimelineEntity t2)")
   TimelineEntity findTimelineEntityWithMaxId();


    List<TimelineEntity> findTimelineEntityByUser_Id(Long id);


    @Query("SELECT t FROM TimelineEntity t WHERE t.user.id = :userId AND t.dateOfPublication >= :startDate")
    List<TimelineEntity> findTimelineEntitiesByUserIdInSpecificTime(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate);

}
