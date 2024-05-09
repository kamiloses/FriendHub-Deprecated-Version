package com.application.friendhub.Repository;

import com.application.friendhub.Entity.TimelineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<TimelineEntity, Long> {
}
