package com.kyriba.conference.api;


import com.kyriba.conference.api.dto.HallRequest;
import com.kyriba.conference.api.dto.HallResponse;
import com.kyriba.conference.domain.Hall;
import com.kyriba.conference.service.HallServiceImpl;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static io.restassured.RestAssured.given;
import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class HallApiTest
{
  @MockBean
  HallServiceImpl hallService;


  @Test
  public void getAllHalls()
  {
    List<Hall> existingHalls = asList(
        new Hall(11L).withName("test11").withPlaces(10),
        new Hall(12L).withName("test12").withPlaces(12));
    doReturn(existingHalls).when(hallService).findAll();

    HallResponse[] halls = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .when()
        .get("/api/v1/halls")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(HallResponse[].class);

    assertNotNull(halls);
    assertEquals(2, halls.length);
    assertEquals(11L, halls[0].getId().longValue());
    assertEquals(12L, halls[1].getId().longValue());
  }


  @Test
  public void addHall()
  {
    doReturn(1L).when(hallService).createHall(any(HallRequest.class));

    Long hallId = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .body("{\n" +
            "  \"hallName\": \"Audience 01\",\n" +
            "  \"places\": \"40\"\n" +
            "}")

        .when()
        .post("/api/v1/halls")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract()
        .jsonPath().getLong("id");

    assertEquals(1L, hallId.longValue());

    // check that Hall was created
//    given()
//        .contentType(APPLICATION_JSON_UTF8_VALUE)
//
//        .when()
//        .get("/api/v1/halls/{id}", hallId)
//
//        .then()
//        .statusCode(HttpStatus.SC_OK)
//        .contentType(APPLICATION_JSON_UTF8_VALUE)
//
//        .content("hallName", equalTo("Audience 01"))
//        .content("places", equalTo("40"));
  }


  @Test
  public void updateHallPlaceCount()
  {
    Long id = 1011L;
    doReturn(id).when(hallService).updateHall(eq(id), any(HallRequest.class));

    Long hallId = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .body("{\n" +
            "  \"hallName\": \"Audience 101\",\n" +
            "  \"places\": \"45\"\n" +
            "}")

        .when()
        .put("/api/v1/halls/" + id)

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract()
        .jsonPath().getLong("id");

    assertEquals(id, hallId);

    // check that Hall was updated
//        .content("name", equalTo("Audience 101"))
//        .content("places", equalTo("45"));

  }


  @Test
  public void removeHall()
  {
    given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .when()
        .delete("/api/v1/halls/1101")
        .then()
        .statusCode(HttpStatus.SC_OK);

    // check that Hall was removed
//    given()
//        .contentType(APPLICATION_JSON_UTF8_VALUE)
//        .body("{\n" +
//            "  \"hallName\": \"Audience 101\",\n" +
//            "  \"places\": \"45\"\n" +
//            "}")
//
//        .when()
//        .put("/api/v1/halls/1101")
//
//        .then()
//        .statusCode(HttpStatus.SC_NOT_FOUND);
  }

}
