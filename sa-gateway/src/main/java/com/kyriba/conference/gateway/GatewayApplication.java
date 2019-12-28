package com.kyriba.conference.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@SpringBootApplication
public class GatewayApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(GatewayApplication.class, args);
  }


  @GetMapping("/fallback")
  public Mono<String> fallback()
  {
    return Mono.just("Fallback");
  }
}
