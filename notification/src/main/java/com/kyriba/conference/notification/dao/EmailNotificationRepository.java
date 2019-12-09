package com.kyriba.conference.notification.dao;

import com.kyriba.conference.notification.domain.EmailNotification;
import com.kyriba.conference.notification.domain.NotificationInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailNotificationRepository extends CrudRepository<EmailNotification, Long> {
   Optional<EmailNotification> findByNotificationInfo(NotificationInfo notificationInfo);
}
