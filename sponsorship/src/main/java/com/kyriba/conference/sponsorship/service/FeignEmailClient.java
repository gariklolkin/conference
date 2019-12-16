package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.EmailMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


// todo update name of notification service
@FeignClient("notification")
@ConditionalOnProperty(value = "notification.sync", havingValue = "true")
public interface FeignEmailClient extends EmailClient
{
  @Override
  default void sendNotification(EmailMessage emailMessage)
  {
    try {
      trySendNotification(emailMessage);
    }
    catch (Exception e) {
      //todo it is workaround, what behavior is expected when the other service is not available?
      e.printStackTrace(); //NOSONAR
    }
  }

  // todo update mapping of notification service
  @PostMapping(value = "/api/notification/email/notify")
  void trySendNotification(@RequestBody EmailMessage emailMessage);
}
