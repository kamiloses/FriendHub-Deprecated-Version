package com.application.friendhub.Repository;

import com.application.friendhub.Entity.PrivateChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChatEntity,Long> {

 @Query("SELECT p FROM PrivateChatEntity p WHERE (p.user1.id = :user1Id AND p.user2.id = :user2Id) " +
         "OR (p.user1.id = :user2Id AND p.user2.id = :user1Id)")
 PrivateChatEntity findPrivateChatEntitiesByUser1_IdAndUser2_Id(@Param("user1Id")Long user1_Id, @Param("user2Id") Long user2_Id);



}
