package com.kyriba.conference.gateway;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Aliaksandr Samal
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(ids = { "com.kyriba.training:sponsorship:+:stubs:8080" },
    stubsMode = StubRunnerProperties.StubsMode.LOCAL)
@ActiveProfiles("test")
class GatewayApplicationTest
{
  @Test
  void registerSponsor()
  {
    //
    Number id = RestAssured
        .given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body("{\n" +
            "  \"name\": \"Aaa Bbb\" ,\n" +
            "  \"email\": \"a@b.org\"\n" +
            "}")
        .when()
        .post("/api/v1/sponsors")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .extract()
        .jsonPath()
        .get("id");
    assertThat(id).isEqualTo(42);
  }
}