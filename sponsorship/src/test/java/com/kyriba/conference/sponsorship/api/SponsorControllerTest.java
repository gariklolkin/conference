package com.kyriba.conference.sponsorship.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


/**
 * @author M-ASL
 * @since v1.0
 */
@ExtendWith({ SpringExtension.class, RestDocumentationExtension.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SponsorControllerTest
{
  private RequestSpecification specification;


  @BeforeEach
  void setUp(RestDocumentationContextProvider restDocumentationContextProvider)
  {
    specification = new RequestSpecBuilder()
        .addFilter(documentationConfiguration(restDocumentationContextProvider))
        .build();
  }


  @Test
  void registerSponsor()
  {
    String id = given(specification)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .filter(document("/api/v1/sponsorship/sponsors"))
        .body("{\n" +
            "  \"name\": \"Alexander Samal\" ,\n" +
            "  \"email\": \"aaa@bbb.by\"\n" +
            "}")
        .when()
        .post("/api/v1/sponsorship/sponsors")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    Assertions.assertNotNull(id);
  }
}