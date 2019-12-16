package com.kyriba.conference.sponsorship.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author M-ASL
 * @since v1.0
 */
@Configuration
public class SwaggerConfiguration
{
  @Bean
  public OpenAPI customOpenAPI()
  {
    return new OpenAPI()
        .components(new Components())
        .info(new Info()
            .title("Sponsorship Service REST API")
            .description("This pages provides RESTful Web Service endpoints for the conference app")
            .version("v1")
            .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
            .contact(new Contact().name("Alexander Samal").email("asamal@kyriba.com")));
  }
}
