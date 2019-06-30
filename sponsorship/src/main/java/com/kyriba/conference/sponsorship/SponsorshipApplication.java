package com.kyriba.conference.sponsorship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//todo uncomment to enable eureka support
//@EnableEurekaClient
@SpringBootApplication
public class SponsorshipApplication
{

  public static void main(String[] args)
  {
    SpringApplication.run(SponsorshipApplication.class, args);
  }
}
