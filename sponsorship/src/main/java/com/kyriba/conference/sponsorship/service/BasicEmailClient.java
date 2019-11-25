package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.EmailMessage;


/**
 * @author Aliaksandr Samal
 */
public interface BasicEmailClient
{
  void sendNotification(EmailMessage emailMessage);
}