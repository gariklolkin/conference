package com.kyriba.conference.sponsorship.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


/**
 * @author M-ASL
 * @since v1.0
 */
@ExtendWith({ SpringExtension.class, RestDocumentationExtension.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class SponsorControllerApiTest extends AbstractContainerBaseTest
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
  void registerSponsor_isOk()
  {
    Number id = given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .filter(document("./api/v1/sponsors"))
        .body("{\n" +
            "  \"name\": \"Alexander Samal\" ,\n" +
            "  \"email\": \"aaa@bbb.by\"\n" +
            "}")
        .when()
        .post("/api/v1/sponsors")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    assertNotNull(id);
  }


  @Test
  void getSponsor_isNotFound()
  {
    Number id = given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .filter(document("./api/v1/sponsors/{id}"))
        .when()
        .get("/api/v1/sponsors/404")
        .then()
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .extract()
        .jsonPath()
        .get("id");
    assertNull(id);
  }


  @Test
  @Sql("/test_data_sponsor.sql")
  void getSponsor_isOk()
  {
    Number id = given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .filter(document("./api/v1/sponsors/{id}"))
        .when()
        .get("/api/v1/sponsors/100")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    assertNotNull(id);
  }


  @Test
  void registerAndDeleteRegisteredSponsor_isNoContent()
  {
    Number id = given(specification)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .filter(document("./api/v1/sponsors"))
        .body("{\n" +
            "  \"name\": \"Alexander Samal\" ,\n" +
            "  \"email\": \"aaa@bbb.by\"\n" +
            "}")
        .when()
        .post("/api/v1/sponsors")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .jsonPath()
        .get("id");

    given(specification)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .filter(document("./api/v1/sponsors/{id}"))
        .when()
        .delete("/api/v1/sponsors/" + id)
        .then()
        .statusCode(HttpStatus.SC_NO_CONTENT);
  }


  @Test
  void deleteSponsor_isNotFound()
  {
    given(specification)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .filter(document("./api/v1/sponsors/{id}"))
        .when()
        .delete("/api/v1/sponsors/123")
        .then()
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }
}