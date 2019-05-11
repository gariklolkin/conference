package com.kyriba.training.notification.api;

import com.kyriba.training.notification.api.dto.EmailNotificationMessage;
import com.kyriba.training.notification.api.dto.MessageStatus;
import com.kyriba.training.notification.api.dto.MessageType;
import com.kyriba.training.notification.api.dto.NotificationStatus;
import com.kyriba.training.notification.api.dto.Recipient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.apache.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EmailNotificationControllerTest
{
  @Test
  public void emailNotificationShouldBeSent()
  {
    List<Recipient> recipients = Arrays.asList(Recipient.builder().name("Name1").address("Address1").build(),
        Recipient.builder().name("Name2").address("Address2").build());

    EmailNotificationMessage emailNotificationMessage = EmailNotificationMessage.builder().recipients(recipients).body("Email Body").subject("Subject").build();


    NotificationStatus status = given()
        .contentType(APPLICATION_JSON_UTF8_VALUE)
//    {"recipients":[{"name":"Name1","address":"Address1"},{"name":"Name2","address":"Address2"}],"body":"Email Body","subject":"Subject","emailMessageOptions":null}
        .body(emailNotificationMessage)
        .when().post("/api/notification/email/notify")

        .then()
        .statusCode(HttpStatus.SC_OK)
        .contentType(APPLICATION_JSON_UTF8_VALUE)

        .extract().body().as(NotificationStatus.class);

    assertNotNull(status);
    assertEquals(status.getMessageStatus(), MessageStatus.ACCEPTED);
    assertNotNull(status.getMessageId());
    assertEquals(status.getMessageType(), MessageType.EMAIL);
  }
}
