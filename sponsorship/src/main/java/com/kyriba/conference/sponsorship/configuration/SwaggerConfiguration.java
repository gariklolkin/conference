package com.kyriba.conference.sponsorship.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


/**
 * @author M-ASL
 * @since v1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration
{
  @Bean
  public Docket productApi()
  {
    Contact contact = new Contact(
        "Alexander Samal",
        "www.kyriba.com",
        "asamal@kyriba.com"
    );

    ApiInfo apiInfo = new ApiInfo(
        "Sponsorship Service REST API",
        "This pages provides RESTful Web Service endpoints for the conference app", "v1",
        "All rights reserved", contact,
        "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());

    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.ant("/api/v1/**"))
        .build()
        .apiInfo(apiInfo);
  }
}
