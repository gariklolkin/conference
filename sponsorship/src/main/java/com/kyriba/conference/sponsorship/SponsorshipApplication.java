package com.kyriba.conference.sponsorship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties
public class SponsorshipApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(SponsorshipApplication.class, args);
  }
}
