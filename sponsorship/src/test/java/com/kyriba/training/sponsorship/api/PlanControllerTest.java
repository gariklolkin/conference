package com.kyriba.training.sponsorship.api;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;


/**
 * @author M-ASL
 * @since 19.2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PlanControllerTest
{
  @Test
  public void registerPlan()
  {
    String id = given()
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .body("{\n" +
            "  \"category\": \"GENERAL\" ,\n" +
            "  \"sponsorId\": \"123\"\n" +
            "}")
        .when()
        .post("/api/v1/sponsorship/plan/register")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    Assert.assertNotNull(id);
  }


  @Test
  public void cancelPlan()
  {
    String id = given()
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .body("{\n" +
            "  \"id\": \"123\"\n" +
            "}")
        .when()
        .post("/api/v1/sponsorship/plan/cancel")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    Assert.assertNotNull(id);
  }
}