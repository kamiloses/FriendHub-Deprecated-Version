package com.application.friendhub.Repository;

import com.application.friendhub.Entity.MessagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessagesEntity, Long> {

    @Query("SELECT m FROM MessagesEntity m WHERE m.privateChats_id.id IN :privateChatId")
    List<MessagesEntity> findAllMessagesRelatedWithPrivateChat(@Param("privateChatId") List<Long> privateChatId);



    List<MessagesEntity> findMessagesEntityBySenderIdOrRecipientsId( Long senderId,Long recipient);



}
