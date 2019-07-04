package com.kyriba.conference.notification.services;

import com.kyriba.conference.notification.domain.dto.EmailNotificationMessage;
import com.kyriba.conference.notification.domain.dto.NotificationStatus;
import com.kyriba.conference.notification.domain.EmailNotification;
import com.kyriba.conference.notification.domain.NotificationInfo;
import com.kyriba.conference.notification.dao.EmailNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailNotificationRequestService
{
  private final EmailNotificationRepository emailNotificationRepository;
  private final AsyncRabbitTemplate asyncRabbitTemplate;


  @Transactional
  public NotificationStatus sendNotification(EmailNotificationMessage emailNotificationMessage) {
    EmailNotification emailNotification = EmailNotification.getInstance(emailNotificationMessage);

    NotificationInfo info = emailNotificationRepository.save(emailNotification).getNotificationInfo();

    info.sendToDeliveryQueue(asyncRabbitTemplate);

    return info.toNotificationStatus();
  }
}
