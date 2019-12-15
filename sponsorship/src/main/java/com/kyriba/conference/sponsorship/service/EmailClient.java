package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.EmailMessage;


/**
 * @author Aliaksandr Samal
 */
public interface EmailClient
{
  void sendNotification(EmailMessage emailMessage);
}
