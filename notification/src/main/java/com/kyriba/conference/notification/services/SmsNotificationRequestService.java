package com.kyriba.conference.notification.services;

import com.kyriba.conference.notification.domain.dto.NotificationStatus;
import com.kyriba.conference.notification.domain.dto.SmsNotificationMessage;
import com.kyriba.conference.notification.domain.NotificationInfo;
import com.kyriba.conference.notification.domain.SmsNotification;
import com.kyriba.conference.notification.dao.SmsNotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SmsNotificationRequestService
{
  private final SmsNotificationRepository smsNotificationRepository;
  private final AsyncRabbitTemplate asyncRabbitTemplate;

  @Transactional
  public NotificationStatus sendNotification(SmsNotificationMessage smsNotificationMessage) {
    SmsNotification smsNotification = SmsNotification.getInstance(smsNotificationMessage);

    NotificationInfo info = smsNotificationRepository.save(smsNotification).getNotificationInfo();

    info.sendToDeliveryQueue(asyncRabbitTemplate);

    return info.toNotificationStatus();
  }
}
