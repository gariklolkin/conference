package com.kyriba.conference.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@SpringBootApplication
@EnableEurekaClient
public class GatewayApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(GatewayApplication.class, args);
  }


  @RequestMapping("/fallback")
  public Mono<String> fallback()
  {
    return Mono.just("Fallback");
  }
}
