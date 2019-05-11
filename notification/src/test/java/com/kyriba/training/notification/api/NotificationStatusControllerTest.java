package com.kyriba.training.notification.api;

import com.kyriba.training.notification.api.dto.MessageStatus;
import com.kyriba.training.notification.api.dto.NotificationStatus;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class NotificationStatusControllerTest
{
  @Test
  public void statusForMessageByIdIsReceived()
  {
    String messageId = "12345";

    NotificationStatus status = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
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
    NotificationStatus[] notifications = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
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
