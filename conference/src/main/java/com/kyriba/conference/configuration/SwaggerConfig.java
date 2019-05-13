package com.kyriba.conference.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalTime;


@Configuration
@EnableSwagger2
public class SwaggerConfig
{
  @Bean
  public Docket api()
  {
    return new Docket(DocumentationType.SWAGGER_2)
        .directModelSubstitute(LocalTime.class, String.class)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.kyriba.conference.api"))
        .build();
  }
}
