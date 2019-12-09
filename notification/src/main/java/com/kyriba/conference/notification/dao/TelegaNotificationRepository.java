package com.kyriba.conference.notification.dao;

import com.kyriba.conference.notification.domain.NotificationInfo;
import com.kyriba.conference.notification.domain.TelegaNotification;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TelegaNotificationRepository extends CrudRepository<TelegaNotification, Long> {
    Optional<TelegaNotification> findByNotificationInfo(NotificationInfo notificationInfo);
}
