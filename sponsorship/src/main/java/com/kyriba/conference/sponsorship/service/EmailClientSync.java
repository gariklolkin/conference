package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.EmailMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


// todo update name of notification service
@FeignClient("notification")
public interface EmailClientSync extends EmailClient
{
  @Override
  // todo update mapping of notification service
  @PostMapping(value = "/api/notification/email/notify")
  void sendNotification(EmailMessage emailMessage);
}
