package com.kyriba.conference.management.api;


import com.kyriba.conference.management.domain.dto.HallResponse;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integrationtest")
public class HallApiTest
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
  public void getExistingHall()
  {
    HallResponse existingHall = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/getOne"))

        .when()
        .get("/api/v1/halls/11")

        .then()
        .statusCode(SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(HallResponse.class);

    assertThat(existingHall).isNotNull();
    assertThat(existingHall.getName()).isEqualTo("test11");
    assertThat(existingHall.getPlaces()).isEqualTo(10);
  }


  @Test
  public void getNonexistentHall()
  {
    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/getNonexistent"))

        .when()
        .get("/api/v1/halls/110099")

        .then()
        .statusCode(SC_NOT_FOUND);
  }


  @Test
  public void getInvalidHallId()
  {
    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .when()
        .get("/api/v1/halls/-11")

        .then()
        .statusCode(SC_INTERNAL_SERVER_ERROR);
  }


  @Test
  public void getAllHalls()
  {
    HallResponse[] halls = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/getAll"))

        .when()
        .get("/api/v1/halls")

        .then()
        .statusCode(SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(HallResponse[].class);

    assertThat(halls).isNotNull();
    assertThat(halls).extracting("name", "places")
        .containsOnly(
            tuple("test11", 10),
            tuple("test13", 13),
            tuple("test12", 12));
  }


  @Test
  @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:afterAddHall.sql")
  public void addHall()
  {
    final String name = "Audience 01";
    final int places = 40;

    Long newHallId = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/add"))
        .body("{\n" +
            "  \"name\": \"" + name + "\",\n" +
            "  \"places\": \"" + places + "\"\n" +
            "}")

        .when()
        .post("/api/v1/halls")

        .then()
        .statusCode(SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().as(HallController.HallCreatedResponse.class)
        .getId();

    // check that Hall is added
    HallResponse newHall = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .when()
        .get("/api/v1/halls/" + newHallId)

        .then()
        .statusCode(SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(HallResponse.class);

    assertThat(newHall).isNotNull();
    assertThat(newHall.getName()).isEqualTo(name);
    assertThat(newHall.getPlaces()).isEqualTo(places);
  }


  @Test
  @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:afterUpdateHallPlaceCount.sql")
  public void updateHallPlaceCount()
  {
    final String name = "Audience 101";
    final int places = 45;

    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/update"))
        .body("{\n" +
            "  \"name\": \"" + name + "\",\n" +
            "  \"places\": \"" + places + "\"\n" +
            "}")

        .when()
        .put("/api/v1/halls/11")

        .then()
        .statusCode(SC_OK);

    // check that Hall is updated
    HallResponse newHall = given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .when()
        .get("/api/v1/halls/11")

        .then()
        .statusCode(SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(HallResponse.class);


    assertThat(newHall).isNotNull();
    assertThat(newHall.getName()).isEqualTo(name);
    assertThat(newHall.getPlaces()).isEqualTo(places);
  }


  @Test
  public void updateNonexistentHall()
  {
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
        .body("message", equalTo("Hall not found."));
  }


  @Test
  @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:afterRemoveHall.sql")
  public void removeHall()
  {
    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("api/v1/halls/delete"))

        .when()
        .delete("/api/v1/halls/13")
        .then()
        .statusCode(SC_NO_CONTENT);

    // check that Hall is removed
    given(documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .when()
        .get("/api/v1/halls/13")

        .then()
        .statusCode(SC_NOT_FOUND);
  }

}
