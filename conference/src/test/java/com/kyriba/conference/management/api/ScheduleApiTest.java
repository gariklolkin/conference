package com.kyriba.conference.management.api;

import com.kyriba.conference.management.api.dto.PresentationRequest;
import com.kyriba.conference.management.api.dto.PresentationResponse;
import com.kyriba.conference.management.api.dto.ScheduleResponse;
import com.kyriba.conference.management.domain.Hall;
import com.kyriba.conference.management.domain.Presentation;
import com.kyriba.conference.management.domain.Topic;
import com.kyriba.conference.management.domain.exception.EntityNotFound;
import com.kyriba.conference.management.domain.exception.LinkedEntityNotFound;
import com.kyriba.conference.management.service.ScheduleService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static java.time.LocalTime.of;
import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
    locations = "classpath:application-integrationtest.properties")
public class ScheduleApiTest
{
  @Rule
  public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

  private RequestSpecification documentationSpec;

  @LocalServerPort
  int port;


  @MockBean
  ScheduleService scheduleService;


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
    doReturn(buildPresentations()).when(scheduleService).getSchedule();

    ScheduleResponse schedule = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/view"))

        .when()
        .get("/api/v1/schedule")

        .then()
        .statusCode(SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(ScheduleResponse.class);

    assertNotNull(schedule);
    assertNotNull(schedule.getPresentations());
    assertEquals(3, schedule.getPresentations().size());
    assertEquals("Ivan Ivanou", schedule.getPresentations().get(2).getTopic().getAuthor());
  }


  @Test
  public void addPresentationInSchedule() throws LinkedEntityNotFound
  {
    Long hallId = 1011L;
    Long expectedPresentationId = 55L;
    doReturn(expectedPresentationId).when(scheduleService).addPresentation(any(PresentationRequest.class));

//    doReturn(Optional.of(new Hall(hallId))).when(hallRepository).findById(eq(hallId));
//    doReturn(Presentation.builder().id(expectedPresentationId).build())
//        .when(presentationRepository).save(any(Presentation.class));

    Long presentationId = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/presentations/add"))
        .body("{\n" +
            "  \"hall\": \"" + hallId + "\",\n" +
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
        .statusCode(SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().as(Long.class);

    assertEquals(expectedPresentationId, presentationId);
  }


  @Test
  public void addPresentationWithNonexistentHall() throws LinkedEntityNotFound
  {
    String hallNotFound = "Hall not found";
    doThrow(new LinkedEntityNotFound(hallNotFound)).when(scheduleService)
        .addPresentation(any(PresentationRequest.class));

    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/presentations/addWIthNonexistentHall"))
        .body("{\n" +
            "  \"hall\": \"1111\",\n" +
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
        .statusCode(SC_BAD_REQUEST)
        .body("message", equalTo(hallNotFound));
  }


  @Test
  public void viewPresentationInfo()
  {
    Long presentationId = 44L;
    String topicTitle = "Spring Data REST";
    doReturn(Optional.of(Presentation.builder()
        .hall(new Hall(1L))
        .topic(new Topic(topicTitle, "Andy Wilkinson"))
        .startTime(of(10, 0))
        .endTime(of(11, 15))
        .build()))
        .when(scheduleService).getPresentation(eq(presentationId));

    PresentationResponse presentation = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/presentations/get"))

        .when()
        .get("/api/v1/schedule/presentations/" + presentationId)

        .then()
        .statusCode(SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(PresentationResponse.class);

    assertEquals(topicTitle, presentation.getTopic().getTitle());
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
        .statusCode(SC_NO_CONTENT);
  }


  @Test
  public void changePresentationTime() throws LinkedEntityNotFound, EntityNotFound
  {
    doNothing().when(scheduleService).updatePresentation(anyLong(), any(PresentationRequest.class));
//    Long presentationId = 55L;
//    Long hallId = 1011L;
//    String topicTitle = "Spring Data REST";
//    Hall hall = new Hall(hallId);
//    doReturn(Optional.of(hall)).when(hallRepository).findById(eq(hallId));
//
//    doReturn(Optional.of(Presentation.builder()
//        .hall(hall)
//        .topic(new Topic(topicTitle, "Andy Wilkinson"))
//        .startTime(of(10, 00))
//        .endTime(of(11, 15))
//        .build()))
//        .when(presentationRepository).findById(eq(presentationId));

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
        .statusCode(SC_OK);
  }


  @Test
  public void changeNonexistentPresentation() throws LinkedEntityNotFound, EntityNotFound
  {
    String presentationNotFound = "Presentation not found";
    doThrow(new EntityNotFound(presentationNotFound))
        .when(scheduleService).updatePresentation(anyLong(), any(PresentationRequest.class));

    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/presentations/updateNonexistent"))
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
        .put("/api/v1/schedule/presentations/555")

        .then()
        .statusCode(SC_NOT_FOUND)
        .body("message", equalTo(presentationNotFound));
  }


  @Test
  public void rearrangePresentationToNonexistentHall() throws LinkedEntityNotFound, EntityNotFound
  {
    String hallNotFound = "Hall not found";
    doThrow(new LinkedEntityNotFound(hallNotFound))
        .when(scheduleService).updatePresentation(anyLong(), any(PresentationRequest.class));

    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/schedule/presentations/updateToNonexistentHall"))
        .body("{\n" +
            "  \"hall\": \"10113242\",\n" +
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
        .statusCode(SC_BAD_REQUEST)
        .body("message", equalTo(hallNotFound));
  }


//  @Test
//  public void rearrangePresentationToAnotherHall()
//  {
//    Long presentationId = 55L;
//    Long newHallId = 301L;
//    String topicTitle = "Spring Data REST";
//    Hall hall = new Hall(newHallId);
//    doReturn(Optional.of(hall)).when(hallRepository).findById(eq(newHallId));
//
//    doReturn(Optional.of(Presentation.builder()
//        .hall(new Hall(1011L))
//        .topic(new Topic(topicTitle, "Andy Wilkinson"))
//        .startTime(of(10, 00))
//        .endTime(of(11, 15))
//        .build()))
//        .when(presentationRepository).findById(eq(presentationId));
//
//    given(documentationSpec)
//        .contentType(APPLICATION_JSON_UTF8_VALUE)
//        .filter(document("api/v1/schedule/presentations/changeHall"))
//        .body("{\n" +
//            "  \"hall\": \"" + newHallId + "\",\n" +
//            "  \"topic\": {\n" +
//            "    \"title\": \"Spring Data REST\",\n" +
//            "    \"author\" : \"Andy Wilkinson\"\n" +
//            "  },\n" +
//            "  \"startTime\": \"10:15\",\n" +
//            "  \"endTime\" : \"11:55\"\n" +
//            "}")
//
//        .when()
//        .put("/api/v1/schedule/presentations/" + presentationId)
//
//        .then()
//        .statusCode(SC_OK);
//  }


  private List<Presentation> buildPresentations()
  {
    return asList(
        Presentation.builder()
            .hall(new Hall(1001L))
            .topic(new Topic("Spring Data REST", "Andy Wilkinson"))
            .startTime(of(10, 0))
            .endTime(of(11, 15))
            .build(),
        Presentation.builder()
            .hall(new Hall(1002L))
            .topic(new Topic("Microservices in practice", "Mikalai Alimenkou"))
            .startTime(of(11, 45))
            .endTime(of(15, 0))
            .build(),
        Presentation.builder()
            .hall(new Hall(1003L))
            .topic(new Topic("All about Spring workshop", "Ivan Ivanou"))
            .startTime(of(10, 0))
            .endTime(of(15, 15))
            .build()
    );
  }
}
