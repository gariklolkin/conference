package com.kyriba.training.notification.services;

import com.kyriba.training.notification.api.dto.EmailNotificationMessage;
import com.kyriba.training.notification.api.dto.MessageStatus;
import com.kyriba.training.notification.api.dto.MessageType;
import com.kyriba.training.notification.api.dto.NotificationStatus;
import org.springframework.stereotype.Service;


@Service
public class EmailNotificationService
{
  public NotificationStatus sendNotification(EmailNotificationMessage emailNotificationMessage) {
    return NotificationStatus.builder().messageId("123").messageType(MessageType.EMAIL).messageStatus(MessageStatus.ACCEPTED).build();
  }
}
