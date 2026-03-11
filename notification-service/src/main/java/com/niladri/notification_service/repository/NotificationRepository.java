package com.niladri.notification_service.repository;

import com.niladri.notification_service.entity.ProcessedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<ProcessedEvent,Long> {

    Optional<Object> findByMessageId(String messageId);
}
