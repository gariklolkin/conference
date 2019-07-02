package com.kyriba.conference.notification.services;

import com.kyriba.conference.notification.api.dto.MessageStatus;
import com.kyriba.conference.notification.api.dto.MessageType;
import com.kyriba.conference.notification.api.dto.NotificationStatus;
import com.kyriba.conference.notification.api.dto.SmsNotificationMessage;
import org.springframework.stereotype.Service;


@Service
public class SmsNotificationService
{
  public NotificationStatus sendNotification(SmsNotificationMessage smsNotificationMessage) {
    return NotificationStatus.builder().messageId("456").messageType(MessageType.SMS).messageStatus(MessageStatus.ACCEPTED).build();
  }
}
