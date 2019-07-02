package com.kyriba.conference.sponsorship.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author Aliaksandr Samal
 */
// todo update name of notification service
@FeignClient("notification")
public interface EmailService
{
  // todo update mapping of notification service
  @PostMapping(value = "/api/notification/email/notify")
  void send(@RequestBody EmailMessage emailMessage);
}
