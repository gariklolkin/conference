package com.kyriba.conference.management.api;


import com.kyriba.conference.management.api.dto.HallRequest;
import com.kyriba.conference.management.api.dto.HallResponse;
import com.kyriba.conference.management.domain.Hall;
import com.kyriba.conference.management.domain.exception.EntityNotFound;
import com.kyriba.conference.management.service.HallService;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
public class HallApiTest
{
  @Rule
  public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

  private RequestSpecification documentationSpec;

  @LocalServerPort
  int port;

  @MockBean
  HallService hallService;


  @Before
  public void setUp()
  {
    RestAssured.port = port;
    documentationSpec = new RequestSpecBuilder()
        .addFilter(documentationConfiguration(restDocumentation)).build();
  }


  @Test
  public void getExistingHall()
  {
    Hall hall = new Hall(11L).withName("test11").withPlaces(10);
    doReturn(of(new HallResponse(hall))).when(hallService).findHall(11L);

    HallResponse existingHall = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/getOne"))

        .when()
        .get("/api/v1/halls/11")

        .then()
        .statusCode(SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(HallResponse.class);

    assertNotNull(existingHall);
    assertEquals(hall.getName(), existingHall.getName());
    assertEquals(hall.getPlaces(), existingHall.getPlaces());
  }


  @Test
  public void getNonexistentHall()
  {
    doReturn(empty()).when(hallService).findHall(anyLong());

    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/getNonexistent"))

        .when()
        .get("/api/v1/halls/110099")

        .then()
        .statusCode(SC_NOT_FOUND);
  }


  @Test
  public void getAllHalls()
  {
    String hall1Name = "test11";
    String hall2Name = "test12";
    int hall1Places = 10;
    int hall2Places = 12;
    List<HallResponse> existingHalls = Stream.of(
        new Hall(11L).withName(hall1Name).withPlaces(hall1Places),
        new Hall(12L).withName(hall2Name).withPlaces(hall2Places))
        .map(HallResponse::new)
        .collect(Collectors.toList());
    doReturn(existingHalls).when(hallService).findAllHalls();

    HallResponse[] halls = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/getAll"))

        .when()
        .get("/api/v1/halls")

        .then()
        .statusCode(SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(HallResponse[].class);

    assertNotNull(halls);
    assertEquals(2, halls.length);
    assertEquals(hall1Name, halls[0].getName());
    assertEquals(hall1Places, halls[0].getPlaces().intValue());
    assertEquals(hall2Name, halls[1].getName());
    assertEquals(hall2Places, halls[1].getPlaces().intValue());
  }


  @Test
  public void addHall()
  {
    Long hallId = 1L;
    doReturn(hallId).when(hallService).createHall(any(HallRequest.class));

    Long responseHallId = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/add"))
        .body("{\n" +
            "  \"name\": \"Audience 01\",\n" +
            "  \"places\": \"40\"\n" +
            "}")

        .when()
        .post("/api/v1/halls")

        .then()
        .statusCode(SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().as(Long.class);

    assertEquals(hallId, responseHallId);
  }


  @Test
  public void updateHallPlaceCount() throws EntityNotFound
  {
    doNothing().when(hallService).updateHall(anyLong(), any(HallRequest.class));

    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/update"))
        .body("{\n" +
            "  \"name\": \"Audience 101\",\n" +
            "  \"places\": \"45\"\n" +
            "}")

        .when()
        .put("/api/v1/halls/1011")

        .then()
        .statusCode(SC_OK);
  }


  @Test
  public void updateNonexistentHall() throws EntityNotFound
  {
    String hallNotFound = "Hall not found";
    doThrow(new EntityNotFound(hallNotFound)).when(hallService).updateHall(anyLong(), any(HallRequest.class));

    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/updateNonexistent"))
        .body("{\n" +
            "  \"name\": \"Audience 101\",\n" +
            "  \"places\": \"45\"\n" +
            "}")

        .when()
        .put("/api/v1/halls/10112323232")

        .then()
        .statusCode(SC_NOT_FOUND)
        .body("message", equalTo(hallNotFound));
  }


  @Test
  public void removeHall()
  {
    doNothing().when(hallService).removeHall(anyLong());

    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/delete"))

        .when()
        .delete("/api/v1/halls/1101")
        .then()
        .statusCode(SC_NO_CONTENT);
  }

}
