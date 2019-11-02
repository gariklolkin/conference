package com.kyriba.conference.sponsorship.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyriba.conference.sponsorship.domain.EmailMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class KafkaEmailClientImpl implements EmailClientAsync
{
  @Value("${notification.kafka.bootstrap.servers}")
  private String kafkaBootstrapServers;
  @Value("${notification.kafka.topic}")
  private String topic;


  @Override
  public void sendNotification(EmailMessage emailMessage)
  {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers); //host.docker.internal
    Producer<String, String> producer = new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
    try {
      ObjectMapper mapper = new ObjectMapper();
      String emailMessageAsString = mapper.writeValueAsString(emailMessage);
      producer.send(new ProducerRecord<>(topic, "emailNotification", emailMessageAsString));
    }
    catch (JsonProcessingException e) {
      // We can't recover from these exceptions, so our only option is to close the producer and exit.
      producer.close();
    }
    producer.close();
  }
}
