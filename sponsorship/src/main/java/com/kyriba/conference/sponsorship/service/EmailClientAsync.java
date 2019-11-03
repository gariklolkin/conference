package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.EmailMessage;


public interface EmailClientAsync
{
  void sendNotification(EmailMessage emailMessage);
}
