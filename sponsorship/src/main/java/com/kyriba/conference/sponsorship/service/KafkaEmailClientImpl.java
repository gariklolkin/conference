package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.configuration.SponsorshipProperties;
import com.kyriba.conference.sponsorship.domain.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "notification.sync", havingValue = "false", matchIfMissing = true)
public class KafkaEmailClientImpl implements EmailClient
{
  private final KafkaTemplate<String, EmailMessage> kafkaTemplate;
  private final SponsorshipProperties properties;


  @Override
  public void sendNotification(EmailMessage emailMessage)
  {
    kafkaTemplate.send(properties.getKafka().getTopic(), emailMessage);
  }
}
