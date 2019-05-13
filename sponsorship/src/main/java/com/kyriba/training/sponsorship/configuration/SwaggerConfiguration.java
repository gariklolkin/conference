/****************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                  *
 * The content of this file is copyrighted by Kyriba Corporation            *
 * and can not be reproduced, distributed, altered or used in any form,     *
 * in whole or in part.                                                     *
 *                                                                          *
 * Date          Author         Changes                                     *
 * 2019-05-13    M-ASL          Created                                     *
 *                                                                          *
 ****************************************************************************/
package com.kyriba.training.sponsorship.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author M-ASL
 * @since 19.2
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration
{
  @Bean
  public Docket productApi()
  {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.kyriba.training.sponsorship.api")).build();
  }
}
