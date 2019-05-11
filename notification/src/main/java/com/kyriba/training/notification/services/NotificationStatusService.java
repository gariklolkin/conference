package com.kyriba.training.notification.services;

import com.kyriba.training.notification.api.dto.MessageStatus;
import com.kyriba.training.notification.api.dto.MessageType;
import com.kyriba.training.notification.api.dto.NotificationStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class NotificationStatusService
{
  public NotificationStatus getStatus(String id) {
    return NotificationStatus.builder().messageId(id).messageType(MessageType.EMAIL).messageStatus(MessageStatus.DELIVERED).build();
  }

  public List<NotificationStatus> getMessagesByStatus(MessageStatus messageStatus) {

    return Arrays.asList(
        NotificationStatus.builder().messageId("123").messageType(MessageType.EMAIL).messageStatus(messageStatus).build(),
        NotificationStatus.builder().messageId("345").messageType(MessageType.SMS).messageStatus(messageStatus).build(),
        NotificationStatus.builder().messageId("456").messageType(MessageType.TELEGA).messageStatus(messageStatus).build());
  }
}
