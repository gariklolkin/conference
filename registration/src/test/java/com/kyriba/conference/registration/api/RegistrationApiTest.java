package com.kyriba.conference.registration.api;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RegistrationApiTest
{

  @LocalServerPort
  int port;

  @Rule
  public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

  private RequestSpecification documentationSpec;


  @Before
  public void setUp()
  {
    RestAssured.port = port;
    documentationSpec = new RequestSpecBuilder()
        .addFilter(documentationConfiguration(restDocumentation)).build();
  }


  @Test
  public void attendeeCanBeRegistered()
  {
    String attendeeId = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/attendees/registration"))
        .body("{\n" +
            "  \"firstName\": \"Ilya\",\n" +
            "  \"lastName\": \"Abashkin\",\n" +
            "  \"email\": \"ilya.a87@gmail.com\",\n" +
            "  \"mobilePhone\": \"+375121234567\",\n" +
            "  \"job\": {\n" +
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
  public void cantRegisterAttendeeWithOutName()
  {
    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/attendees/registration"))
        .body("{\n" +
            "  \"email\": \"ilya.a87@gmail.com\",\n" +
            "  \"mobilePhone\": \"+375121234567\",\n" +
            "  \"job\": {\n" +
            "    \"company\": \"Kyriba Corp.\",\n" +
            "    \"position\": \"Senior Software Engineer\",\n" +
            "    \"city\": \"Minsk\"\n" +
            "  }\n" +
            "}")

        .when()
        .post("/api/v1/attendees")

        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }


  @Test
  public void attendeeCanSeeRegistrationStatus()
  {
    String status = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/attendees/status"))

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
