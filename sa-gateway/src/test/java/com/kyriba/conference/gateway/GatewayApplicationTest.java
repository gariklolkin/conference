package com.kyriba.conference.gateway;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;


@Disabled("This test doesn't work, because it is not clear how to mock a load balancer")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class GatewayApplicationTest
{
  @Autowired
  private WebTestClient webClient;


  @Test
  void contextLoads()
  {
    //Stubs
    stubFor(get(urlEqualTo("/api/v1/sponsorship/plans/10"))
        .willReturn(aResponse()
            .withBody("{\n" +
                "  \"id\": 10,\n" +
                "  \"category\": \"GENERAL\",\n" +
                "  \"sponsorId\": null\n" +
                "}")
            .withHeader("Content-Type", "application/json;charset=UTF-8")
            .withFixedDelay(0)));

    webClient
        .get().uri("/api/v1/sponsorship/plans/10")
        .exchange()
        .expectStatus().isOk()
        .expectBody().json("{\n" +
        "  \"id\": 10,\n" +
        "  \"category\": \"GENERAL\",\n" +
        "  \"sponsorId\": null\n" +
        "}");
  }
}