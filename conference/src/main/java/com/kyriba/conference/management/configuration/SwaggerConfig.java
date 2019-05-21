package com.kyriba.conference.management.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalTime;
import java.util.Collections;


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
        .apis(RequestHandlerSelectors.basePackage("com.kyriba.conference.management.api"))
        .paths(PathSelectors.regex("/api/v1/(halls|schedule).*"))
        .build()
        .apiInfo(apiInfo());
  }


  private ApiInfo apiInfo() {
    return new ApiInfo(
        "Conference Management Service REST API",
        "Conference Management: Halls and Schedule",
        "v1",
        "All rights reserved",
        new Contact("Ilya Abashkin", "www.ilya-a87.com", "ilya.a87@gmail.com"),
        "Single purpose API", "https://www.kyriba.com/api-license", Collections.emptyList());
  }
}
