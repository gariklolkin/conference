package com.kyriba.conference.sponsorship.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author Aliaksandr Samal
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties("notification")
public class SponsorshipProperties
{
  boolean sync;
  KafkaProperties kafka;

  @Setter
  @Getter
  public static class KafkaProperties
  {
    String topic;
  }
}