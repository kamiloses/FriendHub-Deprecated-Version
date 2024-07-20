package com.application.friendhub.Repository;

import com.application.friendhub.Entity.FriendsListEntity;
import com.application.friendhub.Entity.PrivateChatEntity;
import com.application.friendhub.Entity.TimelineEntity;
import com.application.friendhub.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

     Optional<UserEntity> findUserEntityByEmail(String email);

     @Query("SELECT f FROM FriendsListEntity f WHERE f.userEntity.id = :id")
     List<FriendsListEntity> findAllFriendListEntityById(Long id);

     @Query("SELECT t FROM TimelineEntity t WHERE t.user.email = :email")
     List<TimelineEntity> findAllTimelineEntityByEmail(@Param("email") String email);



     UserEntity findUserEntityById(Long id);

     boolean existsByEmail(String email);

     @Query("SELECT s FROM PrivateChatEntity s WHERE s.user1.email = :email or  s.user2.email=:email")
     List<PrivateChatEntity> findAllPrivateChatByEmail(@Param("email") String email);




}
