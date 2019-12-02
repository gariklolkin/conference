package com.kyriba.conference.sponsorship.service;

import com.kyriba.conference.sponsorship.configuration.SponsorshipProperties;
import com.kyriba.conference.sponsorship.domain.EmailMessage;
import com.kyriba.conference.sponsorship.domain.Sponsor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith({ SpringExtension.class })
@SpringBootTest(classes = { SponsorshipProperties.class, KafkaEmailClientImpl.class, KafkaAutoConfiguration.class })
@EnableConfigurationProperties
@EmbeddedKafka(partitions = 1, topics = { "NotificationTopic" },
    brokerProperties = {
        "listeners=PLAINTEXT://localhost:${kafka.broker.port:31003}",
        "auto.create.topics.enable=${kafka.broker.topics-enable:true}",
        "log.dir=out/embedded-kafka" },
    controlledShutdown = true)
@ActiveProfiles("embedded-kafka")
public class KafkaEmailClientImplTest
{
  @Autowired
  KafkaEmailClientImpl client;

  @Autowired
  EmbeddedKafkaBroker embeddedKafka;
  private Consumer<String, String> consumer;


  @BeforeEach
  void setUp()
  {
    Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafka);
    consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    ConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);
    consumer = cf.createConsumer();
    embeddedKafka.consumeFromAnEmbeddedTopic(consumer, "NotificationTopic");
  }


  @Test
  void sendNotification() throws Exception
  {
    //given
    Sponsor sponsor = new Sponsor();
    sponsor.setName("Name");
    sponsor.setEmail("a@b.com");
    EmailMessage message = sponsor.toEmailMessage();
    //when
    client.sendNotification(message);
    //then
    ConsumerRecords<String, String> replies = KafkaTestUtils.getRecords(consumer);
    assertThat(replies.count()).isGreaterThanOrEqualTo(1);
    assertThat(replies.iterator().next().value()).isEqualTo(new ObjectMapper().writeValueAsString(message));
  }
}