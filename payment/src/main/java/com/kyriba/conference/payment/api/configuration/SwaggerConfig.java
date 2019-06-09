package com.kyriba.conference.payment.api.configuration;

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
 * @author Igor Lizura
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket paymentApi() {
        Contact contact = new Contact(
                "Igor Lizura",
                "www.kyriba.com",
                "ilizura@kyriba.com"
        );

        ApiInfo apiInfo = new ApiInfo(
                "Payment Service REST API",
                "This pages provides RESTful Web Service endpoints for the conference app", "v1",
                "All rights reserved", contact,
                "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());


        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kyriba.conference.payment.api"))
                .paths(PathSelectors.regex("/api/v1/(payments|paymentMethods).*"))
                .build()
                .apiInfo(apiInfo);
    }
}
