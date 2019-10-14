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
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ChangeTicketOwnerApiTest
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
  public void attendeeCanChangeTicketsOwner()
  {
    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/tickets/owner"))
        .body("{\n" +
            "  \"ticketOwner\": \"1234\"\n" +
            "}")

        .when()
        .put("/api/v1/tickets/123456789/owner")

        .then()
        .statusCode(HttpStatus.SC_OK);
  }


  @Test
  public void cantChangeTicketsOwnerToNoOne()
  {
    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .body("{\n" +
            "  \"ticketOwner\": \"\"\n" +
            "}")

        .when()
        .put("/api/v1/tickets/123456789/owner")

        .then()
        .statusCode(HttpStatus.SC_BAD_REQUEST);
  }

}
