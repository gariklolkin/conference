package com.kyriba.conference.api;

import com.kyriba.conference.api.dto.PresentationResponse;
import com.kyriba.conference.api.dto.ScheduleResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class ScheduleApiTest
{
  @Test
  public void showSchedule()
  {
    ScheduleResponse schedule = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .when()
        .get("/api/v1/schedule")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(ScheduleResponse.class);

    assertNotNull(schedule);
    assertNotNull(schedule.getPresentations());
    assertEquals(3, schedule.getPresentations().size());
    assertEquals("Ivan Ivanou", schedule.getPresentations().get(2).getTopic().getAuthor());
  }


  @Test
  public void addPresentationInSchedule()
  {
    Long presentationId = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .body("{\n" +
            "  \"hall\": \"1011\",\n" +
            "  \"topic\": {\n" +
            "    \"title\": \"Spring Data REST\",\n" +
            "    \"author\" : \"Andy Wilkinson\"\n" +
            "  },\n" +
            "  \"startTime\": \"10:00 AM\",\n" +
            "  \"endTime\" : \"11:15 AM\"\n" +
            "}")

        .when()
        .post("/api/v1/schedule/presentations")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract()
        .jsonPath().getLong("id");

    assertEquals(55L, presentationId.longValue());
  }


  @Test
  public void viewPresentationInfo()
  {
    PresentationResponse presentation = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .when()
        .get("/api/v1/schedule/presentations/44")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(PresentationResponse.class);

    assertEquals(44L, presentation.getId().longValue());
  }


  @Test
  public void removePresentationFromSchedule()
  {
    given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .when()
        .delete("/api/v1/schedule/presentations/55")
        .then()
        .statusCode(HttpStatus.SC_OK);

    // check that the presentation was removed from schedule
  }


  @Test
  public void changePresentationTime()
  {
    Long presentationId = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .body("{\n" +
            "  \"startTime\": \"10:15 AM\",\n" +
            "  \"endTime\" : \"11:30 AM\"\n" +
            "}")

        .when()
        .patch("/api/v1/schedule/presentations/55")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract()
        .jsonPath().getLong("id");

    assertEquals(55L, presentationId.longValue());
  }


  @Test
  public void rearrangePresentationToAnotherHall()
  {
    Long presentationId = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .body("{\n" +
            "  \"hall\": \"1011\"\n" +
            "}")

        .when()
        .patch("/api/v1/schedule/presentations/55")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract()
        .jsonPath().getLong("id");

    assertEquals(55L, presentationId.longValue());
  }

}
