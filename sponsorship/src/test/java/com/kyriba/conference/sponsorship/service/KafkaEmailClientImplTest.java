package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.EmailMessage;
import com.kyriba.conference.sponsorship.domain.Sponsor;
import org.junit.Test;


public class KafkaEmailClientImplTest
{
  @Test
  public void sendNotification()
  {
    Sponsor sponsor = new Sponsor();
    sponsor.setName("Name");
    sponsor.setEmail("email@tut.by");
    EmailMessage message = sponsor.toEmailMessage();
    EmailClientAsync client = new KafkaEmailClientImpl();
    client.sendNotification(message);
  }
}