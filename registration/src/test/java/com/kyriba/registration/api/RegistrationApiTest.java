package com.kyriba.registration.api;


import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class RegistrationApiTest
{
  @Test
  public void attendeeCanBeRegistered()
  {
    String attendeeId = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .body("{\n" +
            "  \"firstName\": \"Ilya\",\n" +
            "  \"lastName\": \"Abashkin\",\n" +
            "  \"email\": \"ilya.a87@gmail.com\",\n" +
            "  \"mobilePhone\": \"+375121234567\",\n" +
            "  \"jobObject\": {\n" +
            "    \"company\": \"Kyriba Corp.\",\n" +
            "    \"position\": \"Senior Software Engineer\",\n" +
            "    \"city\": \"Minsk\"\n" +
            "  }\n" +
            "}")

        .when()
        .post("/api/v1/attendees")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract()
        .jsonPath().get("id");

    assertNotNull(attendeeId);
  }


  @Test
  public void attendeeCanSeeRegistrationStatus()
  {
    String status = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .when()
        .get("/api/v1/attendees/123456789/status")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract()
        .jsonPath().get("message");

    assertNotNull(status);
  }
}
