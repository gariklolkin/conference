package com.kyriba.conference.notification.services;

import com.kyriba.conference.notification.api.dto.MessageStatus;
import com.kyriba.conference.notification.api.dto.MessageType;
import com.kyriba.conference.notification.api.dto.NotificationStatus;
import com.kyriba.conference.notification.api.dto.TelegaNotificationMessage;
import org.springframework.stereotype.Service;


@Service
public class TelegaNotificationService
{
  public NotificationStatus sendNotification(TelegaNotificationMessage telegaNotificationMessage) {
    return NotificationStatus.builder().messageId("789").messageType(MessageType.TELEGA).messageStatus(MessageStatus.ACCEPTED).build();
  }
}
