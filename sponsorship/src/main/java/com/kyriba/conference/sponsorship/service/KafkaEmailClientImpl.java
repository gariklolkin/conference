package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.domain.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KafkaEmailClientImpl implements EmailClientAsync
{
  private final KafkaTemplate<String, EmailMessage> kafkaTemplate;
  @Value("${notification.kafka.topic}")
  private String topic;


  @Override
  public void sendNotification(EmailMessage emailMessage)
  {
    kafkaTemplate.send(topic, emailMessage);
  }
}
