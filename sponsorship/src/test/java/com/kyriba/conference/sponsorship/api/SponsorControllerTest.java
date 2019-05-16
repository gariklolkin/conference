package com.kyriba.conference.sponsorship.api;

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
 * @since v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SponsorControllerTest
{
  @Test
  public void registerSponsor()
  {
    String id = given()
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .body("{\n" +
            "  \"name\": \"Alexander Samal\" ,\n" +
            "  \"email\": \"a@b.by\"\n" +
            "}")
        .when()
        .post("/api/v1/sponsorship/sponsor/register")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    Assert.assertNotNull(id);
  }
}