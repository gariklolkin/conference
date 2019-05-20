package com.kyriba.conference.notification.services;

import com.kyriba.conference.notification.api.dto.EmailNotificationMessage;
import com.kyriba.conference.notification.api.dto.MessageStatus;
import com.kyriba.conference.notification.api.dto.MessageType;
import com.kyriba.conference.notification.api.dto.NotificationStatus;
import org.springframework.stereotype.Service;


@Service
public class EmailNotificationService
{
  public NotificationStatus sendNotification(EmailNotificationMessage emailNotificationMessage) {
    return NotificationStatus.builder().messageId("123").messageType(MessageType.EMAIL).messageStatus(MessageStatus.ACCEPTED).build();
  }
}
