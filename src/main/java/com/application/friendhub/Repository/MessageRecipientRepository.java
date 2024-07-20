package com.application.friendhub.Repository;

import com.application.friendhub.Entity.MessageRecipientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRecipientRepository extends JpaRepository<MessageRecipientEntity, Long> {
}
