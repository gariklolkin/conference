package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.EmailMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author Aliaksandr Samal
 */
// todo update name of notification service
@FeignClient("notification")
public interface EmailClient
{
  // todo update mapping of notification service
  @PostMapping(value = "/api/notification/email/notify")
  void sendNotification(@RequestBody EmailMessage emailMessage);
}
