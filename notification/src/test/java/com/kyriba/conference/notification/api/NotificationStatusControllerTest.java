package com.kyriba.conference.notification.api;

import com.kyriba.conference.notification.api.dto.MessageStatus;
import com.kyriba.conference.notification.api.dto.NotificationStatus;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class NotificationStatusControllerTest
{
  @Rule
  public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

  private RequestSpecification documentationSpec;

  @Before
  public void setUp() {
    this.documentationSpec = new RequestSpecBuilder()
        .addFilter(documentationConfiguration(restDocumentation))
        .build();
  }

  @Test
  public void statusForMessageByIdIsReceived()
  {
    String messageId = "12345";

    NotificationStatus status = given(this.documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("notification-message-status"))
        .when().get("/api/notification/" + messageId + "/status")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(NotificationStatus.class);

    assertNotNull(status);
    assertEquals(status.getMessageStatus(), MessageStatus.DELIVERED);
    assertEquals(status.getMessageId(), messageId);
  }

  @Test
  public void listOfMessagesByStatusIsReceived()
  {
    NotificationStatus[] notifications = given(this.documentationSpec)
        .contentType(APPLICATION_JSON_UTF8_VALUE)
        .filter(document("notification-status-get"))
        .when().get("/api/notification/status/AWAITING_DELIVERY")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(NotificationStatus[].class);

    assertNotNull(notifications);
    assertEquals(notifications.length, 3);
    assertEquals(notifications[0].getMessageStatus(), MessageStatus.AWAITING_DELIVERY);
    assertEquals(notifications[1].getMessageStatus(), MessageStatus.AWAITING_DELIVERY);
    assertEquals(notifications[2].getMessageStatus(), MessageStatus.AWAITING_DELIVERY);
  }
}
