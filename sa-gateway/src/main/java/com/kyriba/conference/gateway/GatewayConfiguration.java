package com.kyriba.conference.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;


/**
 * @author Aliaksandr Samal
 */
@Configuration
public class GatewayConfiguration
{
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
        .route(r -> r.path("/api/v1/conference/**")
            .filters(f -> f
                .rewritePath("/api/v1/conference/(?<remains>.*)", "/api/v1/${remains}")
                .hystrix(c -> c.setFallbackUri("forward:/fallback")))
            .uri("lb://conference")
            .id("conference"))
        .route(r -> r.path("/api/v1/discount/**")
            .filters(f -> f
                .rewritePath("/api/v1/discount/(?<remains>.*)", "/api/v1/${remains}")
                .hystrix(c -> c.setFallbackUri("forward:/fallback")))
            .uri("lb://discount")
            .id("discount"))
        .build();
  }
}
