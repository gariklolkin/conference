package com.kyriba.conference.notification.dao;

import com.kyriba.conference.notification.domain.NotificationInfo;
import com.kyriba.conference.notification.domain.SmsNotification;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SmsNotificationRepository extends CrudRepository<SmsNotification, Long> {
    Optional<SmsNotification> findByNotificationInfo(NotificationInfo notificationInfo);
}
