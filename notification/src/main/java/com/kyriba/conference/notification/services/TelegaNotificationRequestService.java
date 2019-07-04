package com.kyriba.conference.notification.services;

import com.kyriba.conference.notification.domain.dto.NotificationStatus;
import com.kyriba.conference.notification.domain.dto.TelegaNotificationMessage;
import com.kyriba.conference.notification.dao.TelegaNotificationRepository;
import com.kyriba.conference.notification.domain.NotificationInfo;
import com.kyriba.conference.notification.domain.TelegaNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TelegaNotificationRequestService
{
  private final TelegaNotificationRepository telegaNotificationRepository;
  private final AsyncRabbitTemplate asyncRabbitTemplate;

  @Transactional
  public NotificationStatus sendNotification(TelegaNotificationMessage telegaNotificationMessage) {

    TelegaNotification telegaNotification = TelegaNotification.getInstance(telegaNotificationMessage);

    NotificationInfo info = telegaNotificationRepository.save(telegaNotification).getNotificationInfo();

    info.sendToDeliveryQueue(asyncRabbitTemplate);

    return info.toNotificationStatus();
  }
}
