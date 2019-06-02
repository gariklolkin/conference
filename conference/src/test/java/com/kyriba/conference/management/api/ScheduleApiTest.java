package com.kyriba.conference.management.api;

import com.kyriba.conference.management.api.dto.PresentationResponse;
import com.kyriba.conference.management.api.dto.ScheduleResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduleApiTest
{
  @Rule
  public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

  private RequestSpecification documentationSpec;

  @LocalServerPort
  int port;


  @Before
  public void setUp()
  {
    RestAssured.port = port;
    documentationSpec = new RequestSpecBuilder()
        .addFilter(documentationConfiguration(restDocumentation)).build();
  }


  @Test
  public void showSchedule()
  {
    ScheduleResponse schedule = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/view"))

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
    Long presentationId = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/presentations/add"))
        .body("{\n" +
            "  \"hall\": \"1011\",\n" +
            "  \"topic\": {\n" +
            "    \"title\": \"Spring Data REST\",\n" +
            "    \"author\" : \"Andy Wilkinson\"\n" +
            "  },\n" +
            "  \"startTime\": \"10:00\",\n" +
            "  \"endTime\" : \"11:15\"\n" +
            "}")

        .when()
        .post("/api/v1/schedule/presentations")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().as(Long.class);

    assertEquals(55L, presentationId.longValue());
  }


  @Test
  public void viewPresentationInfo()
  {
    PresentationResponse presentation = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/presentations/get"))

        .when()
        .get("/api/v1/schedule/presentations/44")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(PresentationResponse.class);

    assertEquals("Spring Data REST", presentation.getTopic().getTitle());
  }


  @Test
  public void removePresentationFromSchedule()
  {
    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/presentations/delete"))

        .when()
        .delete("/api/v1/schedule/presentations/55")
        .then()
        .statusCode(HttpStatus.SC_OK);
  }


  @Test
  public void changePresentationTime()
  {
    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/presentations/updateTime"))
        .body("{\n" +
            "  \"hall\": \"1011\",\n" +
            "  \"topic\": {\n" +
            "    \"title\": \"Spring Data REST\",\n" +
            "    \"author\" : \"Andy Wilkinson\"\n" +
            "  },\n" +
            "  \"startTime\": \"10:15\",\n" +
            "  \"endTime\" : \"11:55\"\n" +
            "}")

        .when()
        .put("/api/v1/schedule/presentations/55")

        .then()
        .statusCode(HttpStatus.SC_OK);
  }


  @Test
  public void rearrangePresentationToAnotherHall()
  {
    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/presentations/changeHall"))
        .body("{\n" +
            "  \"hall\": \"301\",\n" +
            "  \"topic\": {\n" +
            "    \"title\": \"Spring Data REST\",\n" +
            "    \"author\" : \"Andy Wilkinson\"\n" +
            "  },\n" +
            "  \"startTime\": \"10:15\",\n" +
            "  \"endTime\" : \"11:55\"\n" +
            "}")

        .when()
        .put("/api/v1/schedule/presentations/55")

        .then()
        .statusCode(HttpStatus.SC_OK);
  }

}
