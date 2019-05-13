package com.kyriba.registration.api;


import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ChangeTicketOwnerApiTest
{

  @Test
  public void attendeeCanChangeTicketsOwner()
  {
    String ticketId = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .body("{\n" +
            "  \"ticketOwner\": \"1234\"\n" +
            "}")

        .when()
        .patch("/api/v1/tickets/123456789/exchange")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract()
        .jsonPath().get("id");

    assertNotNull(ticketId);
  }

}
