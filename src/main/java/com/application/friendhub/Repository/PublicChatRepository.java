package com.application.friendhub.Repository;

import com.application.friendhub.Entity.PublicChatEntity;
import com.application.friendhub.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublicChatRepository extends JpaRepository<PublicChatEntity,Long> {

 List<PublicChatEntity> findAllByUserEntity(UserEntity userEntity);








}
