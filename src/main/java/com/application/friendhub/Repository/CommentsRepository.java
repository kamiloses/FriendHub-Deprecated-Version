package com.application.friendhub.Repository;

import com.application.friendhub.Entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {



    @Query("SELECT t FROM CommentsEntity t WHERE t.userEntity.id = :userId AND t.dateOfPublication >= :startDate")
    List<CommentsEntity> findCommentsEntitiesByUserIdInSpecificTime(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate);



}

