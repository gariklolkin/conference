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
import org.springframework.test.context.ContextConfiguration;
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
@ContextConfiguration(initializers = { AbstractContainerBaseTest.Initializer.class })
class PlanControllerTest extends AbstractContainerBaseTest
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
  void registerPlan()
  {
    String id = given(specification)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .filter(document("/api/v1/sponsorship/plans"))
        .body("{\n" +
            "  \"category\": \"GENERAL\" ,\n" +
            "  \"sponsorEmail\": \"aaa@bbb.org\"\n" +
            "}")
        .when()
        .post("/api/v1/sponsorship/plans")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    Assertions.assertNotNull(id);
  }


  @Test
  void cancelPlan()
  {
    given(specification)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .filter(document("/api/v1/sponsorship/plans/{id}/cancellation"))
        .when()
        .put("/api/v1/sponsorship/plans/123/cancellation")
        .then()
        .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
  }


  @Test
  void getPlan()
  {
    String id = given(specification)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .filter(document("/api/v1/sponsorship/plans/{id}"))
        .when()
        .get("/api/v1/sponsorship/plans/123")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    //todo fill up db with test-data and test the case when returned value != null
    Assertions.assertNull(id);
  }
}