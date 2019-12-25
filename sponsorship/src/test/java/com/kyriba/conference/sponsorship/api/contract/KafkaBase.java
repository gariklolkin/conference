package com.kyriba.conference.sponsorship.api.contract;

import com.kyriba.conference.sponsorship.configuration.SponsorshipProperties;
import com.kyriba.conference.sponsorship.domain.EmailMessage;
import com.kyriba.conference.sponsorship.domain.Sponsor;
import com.kyriba.conference.sponsorship.service.KafkaEmailClientImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


/**
 * @author Aliaksandr Samal
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { SponsorshipProperties.class, KafkaEmailClientImpl.class, KafkaAutoConfiguration.class })
@EnableConfigurationProperties
@EmbeddedKafka(partitions = 1, topics = { "NotificationTopic" },
    brokerProperties = {
        "listeners=PLAINTEXT://localhost:${kafka.broker.port:31008}",
        "auto.create.topics.enable=${kafka.broker.topics-enable:true}",
        "log.dir=out/embedded-kafka" },
    controlledShutdown = true)
@AutoConfigureMessageVerifier
@ActiveProfiles({ "embedded-kafka", "contract-embedded-kafka" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class KafkaBase
{
  @Autowired
  KafkaEmailClientImpl kafkaEmailClient;


  public void trigger()
  {
    Sponsor sponsor = new Sponsor();
    sponsor.setName("Mr. Smith");
    sponsor.setEmail("xxx@yyy.com");
    EmailMessage message = sponsor.toEmailMessage();
    this.kafkaEmailClient.sendNotification(message);
  }
}
