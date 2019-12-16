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
class PlanControllerApiTest extends AbstractContainerBaseTest
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
  void registerPlan_isOk()
  {
    Number id = given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .filter(document("./api/v1/plans"))
        .body("{\n" +
            "  \"category\": \"GENERAL\" ,\n" +
            "  \"sponsorEmail\": \"aaa@bbb.org\"\n" +
            "}")
        .when()
        .post("/api/v1/plans")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    assertNotNull(id);
  }


  @Test
  void registerAndDeleteRegisteredPlan_isNoContent()
  {
    Number id = given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .filter(document("./api/v1/plans"))
        .body("{\n" +
            "  \"category\": \"GENERAL\" ,\n" +
            "  \"sponsorEmail\": \"aaa@bbb.org\"\n" +
            "}")
        .when()
        .post("/api/v1/plans")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .extract()
        .jsonPath()
        .get("id");

    given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .filter(document("./api/v1/plans/{id}"))
        .when()
        .delete("/api/v1/plans/" + id)
        .then()
        .statusCode(HttpStatus.SC_NO_CONTENT);
  }


  @Test
  void deletePlan_isNotFound()
  {
    given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .filter(document("./api/v1/plans/{id}"))
        .when()
        .delete("/api/v1/plans/123")
        .then()
        .statusCode(HttpStatus.SC_NOT_FOUND);
  }


  @Test
  void getPlan_isNotFound()
  {
    Number id = given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .filter(document("./api/v1/plans/{id}"))
        .when()
        .get("/api/v1/plans/404")
        .then()
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    assertNull(id);
  }


  @Test
  @Sql("/test_data_plan.sql")
  void getPlan_isOk()
  {
    Number id = given(specification)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .filter(document("./api/v1/plans/{id}"))
        .when()
        .get("/api/v1/plans/102")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    assertNotNull(id);
  }
}
