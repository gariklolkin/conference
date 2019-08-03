package com.kyriba.conference.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
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


  @Bean
  public RouteLocator myRoutes(RouteLocatorBuilder builder)
  {
    return builder.routes()
        .route(r -> r.path("/api/v1/sponsorship/**")
            .filters(f -> f
                .rewritePath("/api/v1/sponsorship/(?<remains>.*)", "/api/v1/${remains}")
                .setRequestHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .modifyRequestBody(String.class, String.class, MediaType.APPLICATION_JSON_UTF8_VALUE,
                    (serverWebExchange, s) -> Mono.just(s))
                .hystrix(c -> c.setFallbackUri("forward:/fallback")))
            .uri("lb://sponsorship-service")
            .id("sponsorship"))
        .build();
  }


  @RequestMapping("/fallback")
  public Mono<String> fallback()
  {
    return Mono.just("Fallback");
  }
}
