package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.EmailMessage;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author Aliaksandr Samal
 */
public interface EmailClient
{
  void sendNotification(@RequestBody EmailMessage emailMessage);
}
