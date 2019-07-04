package com.kyriba.conference.notification.dao;

import com.kyriba.conference.notification.domain.dto.MessageStatus;
import com.kyriba.conference.notification.domain.NotificationInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface NotificationInfoRepository extends CrudRepository<NotificationInfo, Long> {
    Set<NotificationInfo> findByStatus(MessageStatus status);
    Set<NotificationInfo> findByStatusIn(Set<MessageStatus> statuses);
}

