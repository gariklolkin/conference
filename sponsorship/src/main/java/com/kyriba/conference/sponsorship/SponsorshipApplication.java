package com.kyriba.conference.sponsorship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


//todo uncomment to enable eureka support
//@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class SponsorshipApplication
{

  public static void main(String[] args)
  {
    SpringApplication.run(SponsorshipApplication.class, args);
  }
}
