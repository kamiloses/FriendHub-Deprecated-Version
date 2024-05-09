package com.application.friendhub.Repository;

import com.application.friendhub.Entity.FriendsListEntity;
import com.application.friendhub.Entity.UserEntity;
import com.application.friendhub.Entity.TimelineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

     Optional<UserEntity> findUserEntityByEmail(String email);

     List<FriendsListEntity> findAllFriendListEntityByEmail(String email);

     @Query("SELECT t FROM TimelineEntity t WHERE t.user.email = :email")
     List<TimelineEntity> findAllTimelineEntityByEmail(@Param("email") String email);

     Long findIdByEmail(String email);





}
