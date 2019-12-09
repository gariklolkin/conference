package com.kyriba.conference.notification.services;

import com.kyriba.conference.notification.domain.dto.MessageStatus;
import com.kyriba.conference.notification.domain.dto.NotificationStatus;
import com.kyriba.conference.notification.domain.NotificationInfo;
import com.kyriba.conference.notification.dao.NotificationInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationStatusService
{
  private final NotificationInfoRepository notificationInfoRepository;

  public Optional<NotificationStatus> getStatus(String id) {
    return notificationInfoRepository.findById(Long.parseLong(id)).map(NotificationInfo::toNotificationStatus);
  }

  public List<NotificationStatus> getMessagesByStatus(MessageStatus messageStatus) {
    return notificationInfoRepository.findByStatus(messageStatus).stream().map(NotificationInfo::toNotificationStatus).collect(Collectors.toList());
  }

  public Set<NotificationInfo> findUnsentNotifications() {
    Set<MessageStatus> unsentStatuses = Stream.of(MessageStatus.ACCEPTED, MessageStatus.AWAITING_DELIVERY).collect(Collectors.toSet());
    return notificationInfoRepository.findByStatusIn(unsentStatuses);
  }
}
